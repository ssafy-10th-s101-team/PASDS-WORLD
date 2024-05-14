package world.pasds.back.dashboard.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import world.pasds.back.dashboard.entity.TeamDashboard;
import world.pasds.back.team.entity.Team;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamDashboardRepository extends JpaRepository<TeamDashboard, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<TeamDashboard> findByYearAndMonthAndTeam(int year, int month, Team team);

}
