package world.pasds.back.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.team.entity.PrivateData;
import world.pasds.back.team.entity.Team;

import java.util.List;

@Repository
public interface PrivateDataRepository extends JpaRepository<PrivateData, Long> {

    List<PrivateData> findAllByTeam(Team team);
}
