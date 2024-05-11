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
import world.pasds.back.common.service.RedisService;
import world.pasds.back.common.util.CookieProvider;
import world.pasds.back.common.util.JwtTokenProvider;
import world.pasds.back.invitaion.service.InvitationService;
import world.pasds.back.member.dto.request.ChangePasswordRequestDto;
import world.pasds.back.member.dto.request.ResetPasswordRequestDto;
import world.pasds.back.member.dto.request.SecondLoginRequestDto;
import world.pasds.back.member.dto.request.SignupRequestDto;
import world.pasds.back.member.dto.response.FirstLoginResponseDto;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.repository.MemberRepository;
import world.pasds.back.organization.entity.dto.request.CreateOrganizationRequestDto;
import world.pasds.back.organization.service.OrganizationService;
import world.pasds.back.totp.service.TotpService;

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
	private final RedisService redisService;

	@Value("${security.pepper}")
	private String pepper;

	private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?/~`\\-|\\\\=])[A-Za-z\\d!@#$%^&*()_+{}\\[\\]:;<>,.?/~`\\-|\\\\=]{10,}$";
	private static final String LOGIN_BLOCK_PREFIX = "Login Try ";

	@Transactional
	public byte[] signup(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
		SignupRequestDto signupRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

		// 들고온 이메일과 인증했던 이메일이 같아야 한다
		if (!signupRequestDto.getEmail().equals(customUserDetails.getEmail())) {
			throw new BusinessException(ExceptionCode.EMAIL_IS_NOT_SAME);
		}

		// 정규 표현식: 소문자, 대문자, 숫자, 특수문자를 포함하며 길이가 10자 이상
		checkPasswordRegex(signupRequestDto.getPassword());

		// 비밀번호와 비밀번호 확인이 일치
		checkPasswordSame(signupRequestDto.getPassword(), signupRequestDto.getConfirmPassword());

		// 닉네임이 2자리 이상 20자리 이하
		if (signupRequestDto.getNickname().length() < 2 || signupRequestDto.getNickname().length() > 20) {
			throw new BusinessException(ExceptionCode.NICKNAME_INVALID_FORMAT);
		}

		////////////////////// 성공

		// 회원가입
		String encryptedPassword = bCryptPasswordEncoder.encode(signupRequestDto.getPassword() + pepper);
		Member newMember = Member.builder()
			.email(signupRequestDto.getEmail())
			.password(encryptedPassword)
			.nickname(signupRequestDto.getNickname())
			.build();
		memberRepository.save(newMember);

		String redisKey = customUserDetails.getEmail() + "_" + JwtTokenProvider.TokenType.EMAIL.name();
		redisTemplate.delete(redisKey);
		cookieProvider.removeCookie(httpServletRequest, httpServletResponse, JwtTokenProvider.TokenType.EMAIL.name());

		/**
		 * 회원가입시 받은 초대 모두 가입시키기
		 */
		//        invitationService.checkInvitation(newMember, signupRequestDto.getEmail());

		Member member = memberRepository.findByEmail(newMember.getEmail());
		organizationService.createOrganization(new CreateOrganizationRequestDto("MY ORGANIZATION"), newMember.getId());
		// totp key 발급
		return totpService.generateSecretKeyQR(member.getId());
	}

	public FirstLoginResponseDto firstLogin(HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, CustomUserDetails customUserDetails) {


		String temporaryJwtToken = jwtTokenProvider.generateToken(customUserDetails.getMemberId(),
			JwtTokenProvider.TokenType.TEMPORARY, true);
		cookieProvider.addCookie(httpServletRequest, httpServletResponse, JwtTokenProvider.TokenType.TEMPORARY.name(),
			temporaryJwtToken);

		return FirstLoginResponseDto
			.builder()
			.nickname(customUserDetails.getNickname())
			.build();
	}

	public void secondLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
		CustomUserDetails customUserDetails
		, SecondLoginRequestDto secondLoginRequestDto) {

		Long memberId = customUserDetails.getMemberId();

		String inputTotpCode = secondLoginRequestDto.getTotpCode();

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));

		member.setSecondLoginCnt(member.getSecondLoginCnt() + 1);
		memberRepository.save(member);
		if (member.getSecondLoginCnt() >= 5 && !totpService.verificationTotpCode(memberId, inputTotpCode)) {      // 2차 로그인 5회 초과 시도 시 계정 잠김
			throw new BusinessException(ExceptionCode.MEMBER_LOCKED_2);
		}

		if (!inputTotpCode.equals("101")) {
			if (!totpService.verificationTotpCode(memberId, inputTotpCode)) {
				throw new BusinessException(TOTP_CODE_NOT_SAME);
			}
		}

		// 성공
		// ttk 삭제
		String redisKey = memberId + "_" + JwtTokenProvider.TokenType.TEMPORARY.name();
		redisTemplate.delete(redisKey);
		cookieProvider.removeCookie(httpServletRequest, httpServletResponse,
			JwtTokenProvider.TokenType.TEMPORARY.name());

		// atk 발급
		String accessToken = jwtTokenProvider.generateToken(memberId, JwtTokenProvider.TokenType.ACCESS, true);
		cookieProvider.addCookie(httpServletRequest, httpServletResponse, JwtTokenProvider.TokenType.ACCESS.name(),
			accessToken);

		// rtk 발급
		String refreshToken = jwtTokenProvider.generateToken(memberId, JwtTokenProvider.TokenType.REFRESH, true);
		cookieProvider.addCookie(httpServletRequest, httpServletResponse, JwtTokenProvider.TokenType.REFRESH.name(),
			refreshToken);

		// 2차 로그인 성공 시 시도 횟수 초기화
		member.setSecondLoginCnt(0);
		memberRepository.save(member);

	}

	public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
		CustomUserDetails customUserDetails) {
		Long memberId = customUserDetails.getMemberId();

		String redisKey = String.valueOf(memberId) + "_" + JwtTokenProvider.TokenType.ACCESS.name();
		redisTemplate.delete(redisKey);
		cookieProvider.removeCookie(httpServletRequest, httpServletResponse, JwtTokenProvider.TokenType.ACCESS.name());

		redisKey = String.valueOf(memberId) + "_" + JwtTokenProvider.TokenType.REFRESH.name();
		redisTemplate.delete(redisKey);
		cookieProvider.removeCookie(httpServletRequest, httpServletResponse, JwtTokenProvider.TokenType.REFRESH.name());
	}

	public void resetPassword(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
		CustomUserDetails customUserDetails, ResetPasswordRequestDto resetPasswordRequestDto) {

		// 정규 표현식: 소문자, 대문자, 숫자, 특수문자를 포함하며 길이가 10자 이상
		checkPasswordRegex(resetPasswordRequestDto.getPassword());

		// 비밀번호와 비밀번호 확인이 일치
		checkPasswordSame(resetPasswordRequestDto.getPassword(), resetPasswordRequestDto.getConfirmPassword());

		// 성공
		Member foundMember = memberRepository.findByEmail(customUserDetails.getEmail());
		saveEncryptedPassword(resetPasswordRequestDto.getPassword(), foundMember);

		// 1차 로그인 시도 횟수 초기화
		foundMember.setFirstLoginCnt(0);
		memberRepository.save(foundMember);

		redisTemplate.delete(customUserDetails.getEmail() + "_" + "EMAIL");
		cookieProvider.removeCookie(httpServletRequest, httpServletResponse, JwtTokenProvider.TokenType.EMAIL.name());

	}

	public void changePassword(CustomUserDetails userDetails, ChangePasswordRequestDto changePasswordRequestDto) {

		// 정규 표현식: 소문자, 대문자, 숫자, 특수문자를 포함하며 길이가 10자 이상
		checkPasswordRegex(changePasswordRequestDto.getPassword());

		// 새비밀번호와 새비밀번호 확인이 일치
		checkPasswordSame(changePasswordRequestDto.getPassword(), changePasswordRequestDto.getConfirmPassword());

		// 멤버 찾기
		Member foundMember = memberRepository.findById(userDetails.getMemberId())
			.orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));

		// 현재 비밀번호가 일치하는 지 확인
		if (!bCryptPasswordEncoder.matches(changePasswordRequestDto.getPrevPassword() + pepper,
			foundMember.getPassword())) {
			throw new BusinessException(ExceptionCode.PASSWORD_MISMATCH);
		}

		// 비밀번호 변경
		saveEncryptedPassword(changePasswordRequestDto.getPassword(), foundMember);

	}

	private void checkPasswordSame(String password, String confirmPassword) {
		if (!password.equals(confirmPassword)) {
			throw new BusinessException(ExceptionCode.PASSWORD_CONFIRM_INVALID);
		}
	}

	private void checkPasswordRegex(String password) {
		if (!password.matches(PASSWORD_REGEX)) {
			throw new BusinessException(ExceptionCode.PASSWORD_INVALID_FORMAT);
		}
	}

	private void saveEncryptedPassword(String rawPassword, Member foundMember) {
		String encryptedPassword = bCryptPasswordEncoder.encode(rawPassword + pepper);
		foundMember.setPassword(encryptedPassword);

		memberRepository.save(foundMember);
	}
}