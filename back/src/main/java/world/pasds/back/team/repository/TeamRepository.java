package world.pasds.back.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.team.entity.Team;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByOrganization(Organization organization);

    Boolean existsByOrganizationAndName(Organization organization, String name);

    List<Team> findByIdBetween(Long startId, Long endId);
}
