package world.pasds.back.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.dashboard.entity.OrganizationDashboard;
import world.pasds.back.organization.entity.Organization;

import java.util.Optional;

@Repository
public interface OrganizationDashboardRepository extends JpaRepository<OrganizationDashboard, Long> {

    Optional<OrganizationDashboard> findByYearAndMonthAndOrganization(int year, int month, Organization organization);

}
