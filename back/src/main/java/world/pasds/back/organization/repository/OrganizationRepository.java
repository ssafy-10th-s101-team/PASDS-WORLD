package world.pasds.back.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.organization.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
