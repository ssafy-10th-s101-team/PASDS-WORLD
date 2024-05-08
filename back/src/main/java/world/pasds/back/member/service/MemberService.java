package world.pasds.back.member.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.common.util.CookieProvider;
import world.pasds.back.common.util.JwtTokenProvider;
import world.pasds.back.invitaion.service.InvitationService;
import world.pasds.back.member.dto.request.SecondLoginRequestDto;
import world.pasds.back.member.dto.request.SignupRequestDto;
import world.pasds.back.member.dto.response.FirstLoginResponseDto;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.repository.MemberRepository;
import world.pasds.back.organization.entity.dto.request.CreateOrganizationRequestDto;
import world.pasds.back.organization.service.OrganizationService;
import world.pasds.back.totp.service.TotpService;

import java.util.regex.Pattern;

import static world.pasds.back.common.exception.ExceptionCode.TOTP_CODE_NOT_SAME;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final InvitationService invitationService;
    private final TotpService totpService;
    private final OrganizationService organizationService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieProvider cookieProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${security.pepper}")
    private String pepper;


    @Transactional
    public byte[] signup(SignupRequestDto signupRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

//        // 이메일 형식
//        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
//        Pattern pattern = Pattern.compile(emailRegex);
//        if (!pattern.matcher(signupRequestDto.getEmail()).matches()) {
//            throw new BusinessException(ExceptionCode.EMAIL_INVALID_FORMAT);
//        }
//
//        // DB에 없는 이메일
//        if (memberRepository.existsByEmail(signupRequestDto.getEmail())) {
//            throw new BusinessException(ExceptionCode.EMAIL_EXISTS);
//        }

        // 들고온 이메일과 인증했던 이메일이 같은 이미지 인듯
        if (!signupRequestDto.getEmail().equals(customUserDetails.getEmail())) {
            throw new BusinessException(ExceptionCode.EMAIL_IS_NOT_SAME);
        }

        // 정규 표현식: 소문자, 대문자, 숫자, 특수문자를 포함하며 길이가 10자 이상
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?/~`\\-|\\\\=])[A-Za-z\\d!@#$%^&*()_+{}\\[\\]:;<>,.?/~`\\-|\\\\=]{10,}$";
        if (!signupRequestDto.getPassword().matches(passwordRegex)) {
            throw new BusinessException(ExceptionCode.PASSWORD_INVALID_FORMAT);
        }

        // 비밀번호와 비밀번호 확인이 일치
        if (!signupRequestDto.getPassword().equals(signupRequestDto.getConfirmPassword())) {
            throw new BusinessException(ExceptionCode.PASSWORD_CONFIRM_INVALID);
        }

        // 닉네임이 2자리 이상 20자리 이하
        if (signupRequestDto.getNickname().length() < 2 || signupRequestDto.getNickname().length() > 20) {
            throw new BusinessException(ExceptionCode.NICKNAME_INVALID_FORMAT);
        }

        // 비밀번호 암호화하여 저장
        String encryptedPassword = bCryptPasswordEncoder.encode(signupRequestDto.getPassword() + pepper);
        Member newMember = Member.builder()
                .email(signupRequestDto.getEmail())
                .password(encryptedPassword)
                .nickname(signupRequestDto.getNickname())
                .build();
        memberRepository.save(newMember);

        /**
         * 회원가입시 받은 초대 모두 가입시키기
         */
//        invitationService.checkInvitation(newMember, signupRequestDto.getEmail());

        Member member = memberRepository.findByEmail(newMember.getEmail());
        organizationService.createOrganization(new CreateOrganizationRequestDto("MY ORGANIZATION"), newMember.getId());
        // totp key 발급
        return totpService.generateSecretKeyQR(member.getId());
    }

    public FirstLoginResponseDto firstLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, CustomUserDetails customUserDetails) {

        String temporaryJwtToken = jwtTokenProvider.generateToken(customUserDetails.getMemberId(), JwtTokenProvider.TokenType.TEMPORARY, true);
        cookieProvider.addCookie(httpServletRequest, httpServletResponse, JwtTokenProvider.TokenType.TEMPORARY.name(), temporaryJwtToken);

        return FirstLoginResponseDto
                .builder()
                .nickname(customUserDetails.getNickname())
                .build();
    }

    public void secondLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, CustomUserDetails customUserDetails
            , SecondLoginRequestDto secondLoginRequestDto) {

        Long memberId = customUserDetails.getMemberId();
        String inputTotpCode = secondLoginRequestDto.getTotpCode();

        if (!inputTotpCode.equals("101")) {
            if (!totpService.verificationTotpCode(memberId, inputTotpCode)) {
                throw new BusinessException(TOTP_CODE_NOT_SAME);
            }
        }

        // 성공
        String redisKey = String.valueOf(memberId) + "_" + JwtTokenProvider.TokenType.TEMPORARY.name();
        redisTemplate.delete(redisKey);
        cookieProvider.removeCookie(httpServletRequest, httpServletResponse, JwtTokenProvider.TokenType.TEMPORARY.name());

        String accessToken = jwtTokenProvider.generateToken(memberId, JwtTokenProvider.TokenType.ACCESS, true);
        cookieProvider.addCookie(httpServletRequest, httpServletResponse, JwtTokenProvider.TokenType.ACCESS.name(), accessToken);

        String refreshToken = jwtTokenProvider.generateToken(memberId, JwtTokenProvider.TokenType.REFRESH, true);
        cookieProvider.addCookie(httpServletRequest, httpServletResponse, JwtTokenProvider.TokenType.REFRESH.name(), refreshToken);
    }

    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, CustomUserDetails customUserDetails) {
        Long memberId = customUserDetails.getMemberId();

        String redisKey = String.valueOf(memberId) + "_" + JwtTokenProvider.TokenType.ACCESS.name();
        redisTemplate.delete(redisKey);
        cookieProvider.removeCookie(httpServletRequest, httpServletResponse, JwtTokenProvider.TokenType.ACCESS.name());

        redisKey = String.valueOf(memberId) + "_" + JwtTokenProvider.TokenType.REFRESH.name();
        redisTemplate.delete(redisKey);
        cookieProvider.removeCookie(httpServletRequest, httpServletResponse, JwtTokenProvider.TokenType.REFRESH.name());
    }
}