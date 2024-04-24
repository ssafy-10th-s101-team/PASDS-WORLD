package world.pasds.back.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.team.entity.dto.request.GetTeamsRequestDto;
import world.pasds.back.team.entity.dto.response.GetTeamsResponseDto;
import world.pasds.back.team.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/app/api/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("")
    public ResponseEntity<?> getTeams(@RequestBody GetTeamsRequestDto getTeamsRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<GetTeamsResponseDto> response = teamService.getTeams(getTeamsRequestDto, userDetails.getMemberId());
        return ResponseEntity.ok().body(response);
    }
}
