package world.pasds.back.authority.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.authority.entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
