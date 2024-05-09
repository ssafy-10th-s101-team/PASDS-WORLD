package world.pasds.back.privateData.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.privateData.entity.PrivateData;
import world.pasds.back.team.entity.Team;

import java.util.List;

@Repository
public interface PrivateDataRepository extends JpaRepository<PrivateData, Long> {
    List<PrivateData> findAllByTeam(Team team);

    List<PrivateData> findAllByTeam(Team team, Pageable pageable);
}
