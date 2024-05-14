package world.pasds.back.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.dashboard.entity.TeamDashboard;
import world.pasds.back.dashboard.entity.dto.response.MainDashboardResponseDto;
import world.pasds.back.dashboard.entity.dto.response.TeamDashboardResponseDto;
import world.pasds.back.dashboard.repository.TeamDashboardRepository;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.organization.repository.OrganizationRepository;
import world.pasds.back.team.entity.Team;
import world.pasds.back.team.repository.TeamRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamDashboardService {

    private final TeamRepository teamRepository;
    private final TeamDashboardRepository teamDashboardRepository;
    private final OrganizationRepository organizationRepository;

    @Transactional
    public List<TeamDashboardResponseDto> getTeamDashboard(Long organizationId, int year, int month, char method) {

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        List<Team> teamList = teamRepository.findAllByOrganization(organization);
        List<TeamDashboardResponseDto> teamDashboardResponseDtoList = new ArrayList<>();

        for(Team team : teamList) {

            TeamDashboard teamDashboard = teamDashboardRepository.findByYearAndMonthAndTeam(year, month, team)
                    .orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_DASHBOARD_NOT_FOUNT));

            Long teamId = team.getId();
            String teamName = team.getName();
            int data = 0;

            if(method == 'c') {
                data = teamDashboard.getCount();
            } else if(method == 'v') {
                data = teamDashboard.getViews();
            } else if(method == 'r') {
                data = teamDashboard.getRotate();
            }

            teamDashboardResponseDtoList.add(new TeamDashboardResponseDto(teamId, teamName, data));

        }

        return teamDashboardResponseDtoList;

    }

    @Transactional
    public void checkTeamDashboardDay(Long teamId) {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        Optional<TeamDashboard> existingRecord = teamDashboardRepository
                .findByYearAndMonthAndTeam(year, month, team);

        if(existingRecord.isEmpty()) {
            TeamDashboard teamDashboard = new TeamDashboard();
            teamDashboard.setYear(year);
            teamDashboard.setMonth(month);
            teamDashboard.setTeam(team);
            teamDashboardRepository.save(teamDashboard);
        }

    }

    @Transactional
    public void upTeamDashBoard(Long teamId, char code) {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        TeamDashboard teamDashboard = teamDashboardRepository
                .findByYearAndMonthAndTeam(year, month, team)
                .orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_DASHBOARD_NOT_FOUNT));

        if(code == 'v') {
            teamDashboard.setViews(teamDashboard.getViews() + 1);
        } else if(code == 'c') {
            teamDashboard.setCount(teamDashboard.getCount() + 1);
        } else if(code == 'r') {
            teamDashboard.setRotate(teamDashboard.getRotate() + 1);
        } else if(code == 'm') {
            teamDashboard.setCount(teamDashboard.getCount() - 1);
        }
        teamDashboardRepository.save(teamDashboard);

    }

}
