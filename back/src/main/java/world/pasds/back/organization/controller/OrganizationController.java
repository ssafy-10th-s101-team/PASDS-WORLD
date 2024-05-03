package world.pasds.back.organization.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.organization.entity.dto.request.*;
import world.pasds.back.organization.entity.dto.response.GetOrganizationMemberResponseDto;
import world.pasds.back.organization.entity.dto.response.GetOrganizationsResponseDto;
import world.pasds.back.organization.service.OrganizationService;

import java.util.List;

@RestController
@RequestMapping("/app/api/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping("")
    public ResponseEntity<?> getOrganizations(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<GetOrganizationsResponseDto> response = organizationService.getOrganizations(userDetails.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{organizationId}/{offset}")
    public ResponseEntity<?> getOrganizationMember(@PathVariable(name = "organizationId") Long organizationId, @PathVariable(name = "offset") int offset, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<GetOrganizationMemberResponseDto> response = organizationService.getOrganizationMember(organizationId, offset, userDetails.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrganization(@RequestBody CreateOrganizationRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        organizationService.createOrganization(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rename")
    public ResponseEntity<?> renameOrganization(@RequestBody RenameOrganizationRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        organizationService.renameOrganization(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteOrganization(@RequestBody DeleteOrganizationRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        organizationService.deleteOrganization(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invite")
    public ResponseEntity<?> inviteMemberToOrganization(@RequestBody InviteMemberToOrganizationRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        organizationService.inviteMemberToOrganization(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeMemberFromOrganization(@RequestBody RemoveMemberFromOrganizationRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        organizationService.removeMemberFromOrganization(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leaveTeam(@RequestBody LeaveOrganizationRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        organizationService.leaveOrganization(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignNewHeader(@RequestBody AssignNewHeaderRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        organizationService.assignNewHeader(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }
}
