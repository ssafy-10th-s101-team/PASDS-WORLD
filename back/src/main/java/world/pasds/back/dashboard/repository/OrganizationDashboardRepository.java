package world.pasds.back.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.organization.entity.Organization;

@Repository
public interface OrganizationDashboardRepository extends JpaRepository<Organization, Long> {



}
