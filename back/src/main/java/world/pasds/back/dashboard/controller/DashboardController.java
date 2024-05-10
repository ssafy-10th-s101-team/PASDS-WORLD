package world.pasds.back.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.pasds.back.dashboard.entity.dto.response.MainDashboardResponseDto;
import world.pasds.back.dashboard.service.OrganizationDashboardService;

@RestController
@RequestMapping("/app/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private OrganizationDashboardService organizationDashboardService;

    @GetMapping("{organizationId}")
    public ResponseEntity<MainDashboardResponseDto> getMainDashboard(@PathVariable(name = "organizationId") Long organizationId) {
        MainDashboardResponseDto respoonse = organizationDashboardService.getMainDashboard(organizationId);
        return ResponseEntity.ok().body(respoonse);
    }

}
