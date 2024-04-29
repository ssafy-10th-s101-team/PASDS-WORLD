package world.pasds.back.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import world.pasds.back.member.dto.request.SignupRequestDto;
import world.pasds.back.member.dto.response.SignupResponseDto;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.member.service.MemberService;

@RestController
@RequestMapping("/app/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.status(HttpStatus.OK).body("test");
    }

    @PostMapping("/jwt-test")
    public ResponseEntity<?> jwtTest(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(userDetails.getMemberId());
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.signup(signupRequestDto));
    }

}
