package world.pasds.back.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.team.entity.dto.request.*;
import world.pasds.back.team.entity.dto.response.GetPrivateDataListResponseDto;
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

    @GetMapping("/list")
    public ResponseEntity<?> getPrivateDataList(@RequestBody GetPrivateDataListRequestDto getPrivateDataListRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<GetPrivateDataListResponseDto> response = teamService.getPrivateDataList(getPrivateDataListRequestDto, userDetails.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTeam(@RequestBody CreateTeamRequestDto createTeamRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        teamService.createTeam(createTeamRequestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteTeam(@RequestBody DeleteTeamRequestDto deleteTeamRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        teamService.deleteTeam(deleteTeamRequestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invite")
    public ResponseEntity<?> inviteMemberToTeam(@RequestBody InviteMemberToTeamRequestDto inviteMemberToTeamRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        teamService.inviteMemberToTeam(inviteMemberToTeamRequestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }
}
