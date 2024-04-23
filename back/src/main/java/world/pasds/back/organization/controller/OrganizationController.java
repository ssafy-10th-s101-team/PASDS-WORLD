package world.pasds.back.organization.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.organization.entity.dto.request.CreateOrganizationRequestDto;
import world.pasds.back.organization.entity.dto.request.DeleteOrganizationRequestDto;
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

    @PostMapping("/create")
    public ResponseEntity<?> createOrganization(@RequestBody CreateOrganizationRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        String name = requestDto.getName();
        organizationService.createOrganization(name, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteOrganization(@RequestBody DeleteOrganizationRequestDto deleteOrganizationRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        organizationService.deleteOrganization(deleteOrganizationRequestDto.getOrganizationId(), userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

}
