package world.pasds.back.organization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.invitaion.service.InvitationService;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.entity.MemberOrganization;
import world.pasds.back.member.entity.MemberTeam;
import world.pasds.back.member.repository.MemberOrganizationRepository;
import world.pasds.back.member.repository.MemberRepository;
import world.pasds.back.member.repository.MemberTeamRepository;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.organization.entity.dto.request.CreateOrganizationRequestDto;
import world.pasds.back.organization.entity.dto.request.DeleteOrganizationRequestDto;
import world.pasds.back.organization.entity.dto.request.InviteMemberToOrganizationRequestDto;
import world.pasds.back.organization.entity.dto.request.RemoveMemberFromOrganizationRequestDto;
import world.pasds.back.organization.entity.dto.response.GetOrganizationsResponseDto;
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
    private final InvitationService invitationService;
    private final MemberTeamRepository memberTeamRepository;

    @Transactional
    public void createOrganization(CreateOrganizationRequestDto requestDto, Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));

        Organization o = Organization.builder()
                .header(findMember)
                .name(requestDto.getName())
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
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        List<MemberOrganization> organizations = memberOrganizationRepository.findAllByMember(findMember);
        return organizations.stream().map(org -> new GetOrganizationsResponseDto(org.getOrganization().getId(), org.getOrganization().getName(), org.getOrganization().getTeamCount())).collect(Collectors.toList());
    }

    @Transactional
    public void deleteOrganization(DeleteOrganizationRequestDto requestDto, Long memberId) {
        Organization findOrganization = organizationRepository.findById(requestDto.getOrganizationId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        if (findOrganization.getHeader().getId() != memberId) {
            throw new BusinessException(ExceptionCode.ORGANIZATION_UNAUTHORIZED);
        }

        List<Team> fidnTeamList = teamRepository.findAllByOrganization(findOrganization);
        teamRepository.deleteAll(fidnTeamList);
        organizationRepository.delete(findOrganization);
    }

    public void inviteMemberToOrganization(InviteMemberToOrganizationRequestDto requestDto, Long memberId) {
        Member sender = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization findOrganization = organizationRepository.findById(requestDto.getOrganizationId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        /**
         * Todo 권한 확인
         */

        invitationService.inviteMemberToOrganization(findOrganization, sender, requestDto.getEmail());

        Member receiver = memberRepository.findByEmail(requestDto.getEmail());
        if (receiver != null) {
            /**
             * Todo 알림 구현
             */
        }
    }

    public void removeMemberFromOrganization(RemoveMemberFromOrganizationRequestDto requestDto, Long memberId) {
        Organization findOrganization = organizationRepository.findById(requestDto.getOrganizationId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Member removeMember = memberRepository.findByEmail(requestDto.getEmail());
        if (removeMember == null) {
            throw new BusinessException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        MemberOrganization findMemberAndOrganization = memberOrganizationRepository.findByMemberAndOrganization(removeMember, findOrganization);

        if (findMemberAndOrganization == null) {
            throw new BusinessException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        /**
         * Todo 권한 확인
         */

        List<Team> findTeamList = teamRepository.findAllByOrganization(findOrganization);

        for (Team team : findTeamList) {
            MemberTeam findTeamMember = memberTeamRepository.findByMemberAndTeam(removeMember, team);
            memberTeamRepository.delete(findTeamMember);
        }
    }
}
