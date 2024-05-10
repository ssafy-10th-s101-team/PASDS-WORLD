package world.pasds.back.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.dashboard.entity.TeamDashboard;
import world.pasds.back.dashboard.repository.TeamDashboardRepository;
import world.pasds.back.team.entity.Team;
import world.pasds.back.team.repository.TeamRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TeamRepository teamRepository;
    private final TeamDashboardRepository teamDashboardRepository;

    @Transactional
    public void checkTeamDay(Long teamId) {
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
    public void upTeamView(Long teamId) {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));
        //here

    }

}
