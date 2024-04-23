package world.pasds.back.organization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.exception.UnauthorizedException;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.entity.MemberOrganization;
import world.pasds.back.member.exception.MemberNotFoundException;
import world.pasds.back.member.repository.MemberOrganizationRepository;
import world.pasds.back.member.repository.MemberRepository;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.organization.entity.dto.response.GetOrganizationsResponseDto;
import world.pasds.back.organization.exception.OrganizationNotFoundException;
import world.pasds.back.organization.repository.OrganizationRepository;
import world.pasds.back.team.entity.Team;
import world.pasds.back.team.repository.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final MemberOrganizationRepository memberOrganizationRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void createOrganization(String name, Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("없는 유저입니다."));

        Organization o = Organization.builder()
                .header(findMember)
                .name(name)
                .teamCount(0)
                .build();
        organizationRepository.save(o);

        MemberOrganization mo = MemberOrganization.builder()
                .organization(o)
                .member(findMember)
                .build();
        memberOrganizationRepository.save(mo);
    }

    @Transactional
    public List<GetOrganizationsResponseDto> getOrganizations(Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("없는 유저입니다."));
        List<MemberOrganization> organizations = memberOrganizationRepository.findAllByMember(findMember);
        return organizations.stream().map(org -> new GetOrganizationsResponseDto(org.getOrganization().getId(), org.getOrganization().getName(), org.getOrganization().getTeamCount())).collect(Collectors.toList());
    }

    @Transactional
    public void deleteOrganization(Long organizationId, Long memberId) {
        Organization findOrganization = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationNotFoundException("없는 조직입니다."));
        if (findOrganization.getHeader().getId() != memberId) {
            throw new UnauthorizedException("조직 삭제는 조직장만이 가능합니다.");
        }

        List<Team> fidnTeamList = teamRepository.findAllByOrganization(findOrganization);
        teamRepository.deleteAll(fidnTeamList);
        organizationRepository.delete(findOrganization);
    }

}
