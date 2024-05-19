package world.pasds.back.dashboard.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import world.pasds.back.dashboard.entity.OrganizationDashboard;
import world.pasds.back.organization.entity.Organization;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationDashboardRepository extends JpaRepository<OrganizationDashboard, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<OrganizationDashboard> findByYearAndMonthAndOrganization(int year, int month, Organization organization);
    List<OrganizationDashboard> findAllByOrganization(Organization organization);

}
