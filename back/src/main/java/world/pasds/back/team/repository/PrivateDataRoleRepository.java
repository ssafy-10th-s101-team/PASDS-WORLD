package world.pasds.back.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.role.entity.Role;
import world.pasds.back.team.entity.PrivateData;
import world.pasds.back.team.entity.PrivateDataRole;

import java.util.List;

@Repository
public interface PrivateDataRoleRepository extends JpaRepository<PrivateDataRole, Long> {

    List<PrivateDataRole> findAllByPrivateData(PrivateData privateData);

    boolean existsByPrivateDataAndRole(PrivateData privateData, Role role);
}
