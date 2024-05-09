package world.pasds.back.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import world.pasds.back.member.dto.request.ChangePasswordRequestDto;
import world.pasds.back.member.dto.request.ResetPasswordRequestDto;
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
    public ResponseEntity<?> signup(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    @RequestBody SignupRequestDto signupRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(memberService.signup(httpServletRequest, httpServletResponse, signupRequestDto, customUserDetails));
    }

    @PostMapping("/first-login")
    public ResponseEntity<?> firstLogin(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.firstLogin(httpServletRequest, httpServletResponse, customUserDetails));
    }

    @PostMapping("/second-login")
    public ResponseEntity<?> secondLogin(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse, @AuthenticationPrincipal CustomUserDetails customUserDetails
            , @RequestBody SecondLoginRequestDto secondLoginRequestDto) {
        memberService.secondLogin(httpServletRequest, httpServletResponse, customUserDetails, secondLoginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        memberService.logout(httpServletRequest, httpServletResponse, customUserDetails);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
        memberService.resetPassword(httpServletRequest, httpServletResponse, customUserDetails, resetPasswordRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        memberService.changePassword(httpServletRequest, httpServletResponse, userDetails, changePasswordRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
