package world.pasds.back.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.dashboard.entity.OrganizationDashboard;
import world.pasds.back.dashboard.repository.OrganizationDashboardRepository;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.organization.repository.OrganizationRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationDashboardService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationDashboardRepository organizationDashboardRepository;

    @Transactional
    public void checkOrganizationDashboardDay(Long OrganizationId) {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        Organization organization = organizationRepository.findById(OrganizationId)
                .orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        Optional<OrganizationDashboard> existingRecord = organizationDashboardRepository
                .findByYearAndMonthAndOrganization(year, month, organization);

        if(existingRecord.isEmpty()) {
            OrganizationDashboard organizationDashboard = new OrganizationDashboard();
            organizationDashboard.setYear(year);
            organizationDashboard.setMonth(month);
            organizationDashboard.setOrganization(organization);
            organizationDashboardRepository.save(organizationDashboard);
        }

    }

    @Transactional
    public void upOrganizationDashBoard(Long OrganizationId, char code) {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        Organization Organization = organizationRepository.findById(OrganizationId)
                .orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        OrganizationDashboard organizationDashboard = organizationDashboardRepository
                .findByYearAndMonthAndOrganization(year, month, Organization)
                .orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_DASHBOARD_NOT_FOUNT));

        if(code == 'v') {
            organizationDashboard.setViews(organizationDashboard.getViews() + 1);
        } else if(code == 'c') {
            organizationDashboard.setCount(organizationDashboard.getCount() + 1);
        } else if(code == 'r') {
            organizationDashboard.setRotate(organizationDashboard.getRotate() + 1);
        } else if(code == 'm') {
            organizationDashboard.setCount(organizationDashboard.getCount() - 1);
        }
        organizationDashboardRepository.save(organizationDashboard);

    }

}
