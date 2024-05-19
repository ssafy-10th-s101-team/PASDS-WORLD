package world.pasds.back.invitaion.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import world.pasds.back.invitaion.entity.dto.request.AcceptOrganizationInviteRequestDto;
import world.pasds.back.invitaion.entity.dto.request.AcceptTeamInviteRequestDto;
import world.pasds.back.invitaion.entity.dto.response.*;
import world.pasds.back.invitaion.service.InvitationService;
import world.pasds.back.member.entity.CustomUserDetails;

import java.util.List;

@RestController
@RequestMapping("/app/api/invitation")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping("")
    public ResponseEntity<?> getInvitations(@RequestParam(name = "offset") int offset, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<GetInvitationsResponseDto> response = invitationService.getInvitations(offset, userDetails.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/accept")
    public ResponseEntity<?> acceptOrganizationInvite(@RequestBody AcceptOrganizationInviteRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        AcceptResponseDto response = invitationService.acceptOrganizationInvite(requestDto, userDetails.getMemberId());

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectOrganizationInvite(@RequestBody RejectOrganizationInviteRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        RejectResponseDto response = invitationService.rejectOrganizationInvite(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/accept-team")
    public ResponseEntity<?> acceptTeamInvite(@RequestBody AcceptTeamInviteRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        invitationService.acceptTeamInvite(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject-team")
    public ResponseEntity<?> rejectTeamInvite(@RequestBody RejectTeamInviteRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        invitationService.rejectTeamInvite(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }
}
