package world.pasds.back.privateData.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.privateData.entity.PrivateData;
import world.pasds.back.team.entity.Team;

import java.util.List;

@Repository
public interface PrivateDataRepository extends JpaRepository<PrivateData, Long>, PrivateDataCustomRepository {
    List<PrivateData> findAllByTeam(Team team);

}
