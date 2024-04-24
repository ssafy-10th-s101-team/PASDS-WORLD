package world.pasds.back.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import world.pasds.back.member.dto.request.SignupRequestDto;
import world.pasds.back.member.dto.response.SignupResponseDto;
import world.pasds.back.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {

        String encryptedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        System.out.println(encryptedPassword);

        return null;

//        passwordEncoder.matches(rawPassword, encodedPassword)

        // TODO: 이메일: 이메일 형식이 맞아야 함

        // TODO: 이메일: DB에 없는 이메일이여야 함

        // TODO: 이메일 인증: 이메일 인증을 거쳐야 함

        // TODO: 비밀번호: 대문자, 소문자, 특수기호 숫자 최소 10자리 이상이어야 함

        // TODO: 비밀번호 확인: 비밀번호와 일치해야 함

        // TODO: 닉네임: 2자리 이상 10자리 이하여야 함

        // TODO: TOTP


    }
}
