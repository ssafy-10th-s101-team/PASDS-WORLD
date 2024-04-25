package world.pasds.back.invitaion.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.pasds.back.invitaion.entity.dto.request.AcceptOrganizationInviteRequestDto;
import world.pasds.back.invitaion.entity.dto.request.AcceptTeamInviteRequestDto;
import world.pasds.back.invitaion.entity.dto.response.RejectOrganizationInviteRequestDto;
import world.pasds.back.invitaion.entity.dto.response.RejectTeamInviteRequestDto;
import world.pasds.back.invitaion.service.InvitationService;
import world.pasds.back.member.entity.CustomUserDetails;

@RestController
@RequestMapping("/app/api/invitation")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping("/accept")
    public ResponseEntity<?> acceptOrganizationInvite(@RequestBody AcceptOrganizationInviteRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        invitationService.acceptOrganizationInvite(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectOrganizationInvite(@RequestBody RejectOrganizationInviteRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        invitationService.rejectOrganizationInvite(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept")
    public ResponseEntity<?> acceptTeamInvite(@RequestBody AcceptTeamInviteRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        invitationService.acceptTeamInvite(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectTeamInvite(@RequestBody RejectTeamInviteRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        invitationService.rejectTeamInvite(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }
}
