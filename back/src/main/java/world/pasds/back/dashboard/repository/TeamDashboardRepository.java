package world.pasds.back.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.dashboard.entity.TeamDashboard;
import world.pasds.back.team.entity.Team;

import java.util.Optional;

@Repository
public interface TeamDashboardRepository extends JpaRepository<TeamDashboard, Long> {

    Optional<TeamDashboard> findByYearAndMonthAndTeam(int year, int month, Team team);

}
