package world.pasds.back.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.role.entity.Role;
import world.pasds.back.role.entity.RoleAuthority;

import java.util.List;

@Repository
public interface RoleAuthorityRepository extends JpaRepository<RoleAuthority, Long> {

    List<RoleAuthority> findAllByRole(Role role);
}
