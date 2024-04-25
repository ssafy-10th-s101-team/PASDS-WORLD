package world.pasds.back.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.role.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
