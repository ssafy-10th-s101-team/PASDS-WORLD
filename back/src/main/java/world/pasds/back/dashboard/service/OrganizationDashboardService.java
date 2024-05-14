package world.pasds.back.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.dashboard.entity.OrganizationDashboard;
import world.pasds.back.dashboard.entity.TeamDashboard;
import world.pasds.back.dashboard.entity.dto.response.MainDashboardResponseDto;
import world.pasds.back.dashboard.repository.OrganizationDashboardRepository;
import world.pasds.back.dashboard.repository.TeamDashboardRepository;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.organization.repository.OrganizationRepository;
import world.pasds.back.team.entity.Team;
import world.pasds.back.team.repository.TeamRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationDashboardService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationDashboardRepository organizationDashboardRepository;

    @Transactional
    public MainDashboardResponseDto getMainDashboard(Long organizationId) {

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        List<OrganizationDashboard> organizationDashboardList = organizationDashboardRepository.findByOrganization(organization);
//        System.out.println(organizationDashboardList.size() + "하잉잉");
        MainDashboardResponseDto mainDashboardResponseDto = new MainDashboardResponseDto();

        for(OrganizationDashboard organizationDashboard : organizationDashboardList) {
            int year = organizationDashboard.getYear();
            int mon = organizationDashboard.getMonth();
            int view = organizationDashboard.getViews();
            int count = organizationDashboard.getCount();
            int rotate = organizationDashboard.getRotate();

            mainDashboardResponseDto.getOrganizationCountList().add(new int[] {year, mon, count});
            mainDashboardResponseDto.getOrganizationViewList().add(new int[] {year, mon, view});
            mainDashboardResponseDto.getOrganizationRotateList().add(new int[] {year, mon, rotate});
        }

        return mainDashboardResponseDto;

    }

    @Transactional
    public void checkOrganizationDashboardDay(Long organizationId) {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        Organization organization = organizationRepository.findById(organizationId)
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
    public void upOrganizationDashBoard(Long organizationId, char code) {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        Organization Organization = organizationRepository.findById(organizationId)
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
