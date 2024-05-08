package world.pasds.back.member.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import world.pasds.back.member.dto.request.SecondLoginRequestDto;
import world.pasds.back.member.dto.request.SignupRequestDto;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.member.service.MemberService;

@RestController
@RequestMapping("/app/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/jwt-test")
    public ResponseEntity<?> jwtTest(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(userDetails.getMemberId());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(memberService.signup(signupRequestDto));
    }

    @PostMapping("/first-login")
    public ResponseEntity<?> firstLogin(HttpServletResponse httpServletResponse, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.firstLogin(httpServletResponse, customUserDetails));
    }

    @PostMapping("/second-login")
    public ResponseEntity<?> secondLogin(HttpServletResponse httpServletResponse, @AuthenticationPrincipal CustomUserDetails customUserDetails
            , @RequestBody SecondLoginRequestDto secondLoginRequestDto) {
        memberService.secondLogin(httpServletResponse, customUserDetails, secondLoginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse httpServletResponse, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        memberService.logout(httpServletResponse, customUserDetails);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
