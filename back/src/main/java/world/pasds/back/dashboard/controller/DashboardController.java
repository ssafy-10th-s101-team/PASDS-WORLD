package world.pasds.back.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import world.pasds.back.dashboard.entity.dto.response.MainDashboardResponseDto;
import world.pasds.back.dashboard.entity.dto.response.TeamDashboardResponseDto;
import world.pasds.back.dashboard.service.OrganizationDashboardService;
import world.pasds.back.dashboard.service.TeamDashboardService;
import world.pasds.back.member.entity.CustomUserDetails;

import java.util.List;

@RestController
@RequestMapping("/app/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final OrganizationDashboardService organizationDashboardService;
    private final TeamDashboardService teamDashboardService;

    @GetMapping("{organizationId}")
    public ResponseEntity<MainDashboardResponseDto> getMainDashboard(
            @PathVariable(name = "organizationId") Long organizationId,
             @AuthenticationPrincipal CustomUserDetails userDetails) {
        MainDashboardResponseDto response = organizationDashboardService.getMainDashboard(organizationId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<TeamDashboardResponseDto>> getTeamDashboard(
        @RequestParam(value = "organizationId", required = false) Long organizationId,
        @RequestParam(value = "year", required = false) int year,
        @RequestParam(value = "month", required = false) int month,
        @RequestParam(value = "method", required = false) char method
    ) {
        List<TeamDashboardResponseDto> response = teamDashboardService.getTeamDashboard(organizationId, year, month, method);
        return ResponseEntity.ok().body(response);
    }

}
