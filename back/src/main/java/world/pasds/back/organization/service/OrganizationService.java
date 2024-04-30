package world.pasds.back.organization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.invitaion.service.InvitationService;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.entity.MemberOrganization;
import world.pasds.back.member.entity.MemberRole;
import world.pasds.back.member.entity.MemberTeam;
import world.pasds.back.member.repository.MemberOrganizationRepository;
import world.pasds.back.member.repository.MemberRepository;
import world.pasds.back.member.repository.MemberRoleRepository;
import world.pasds.back.member.repository.MemberTeamRepository;
import world.pasds.back.notification.entity.NotificationType;
import world.pasds.back.notification.service.NotificationService;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.organization.entity.dto.request.*;
import world.pasds.back.organization.entity.dto.response.GetOrganizationsResponseDto;
import world.pasds.back.organization.repository.OrganizationRepository;
import world.pasds.back.role.entity.Role;
import world.pasds.back.role.repository.RoleRepository;
import world.pasds.back.team.entity.Team;
import world.pasds.back.team.entity.dto.request.CreateTeamRequestDto;
import world.pasds.back.team.repository.TeamRepository;
import world.pasds.back.team.service.TeamService;

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
    private final MemberRoleRepository memberRoleRepository;
    private final RoleRepository roleRepository;
    private final NotificationService notificationService;
    private final TeamService teamService;

    @Transactional
    public void createOrganization(CreateOrganizationRequestDto requestDto, Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));

        // 개인 고유 조직이 존재할 때
        if (organizationRepository.existsByHeaderAndName(findMember, "MY ORGANIZATION")) {
            // 개인 고유 조직과 같은 이름의 조직 생성 불가
            if (isMyOrganization(requestDto.getName())) {
                throw new BusinessException(ExceptionCode.BAD_REQUEST);
            }
            createAndSaveOrganizationWithMember(requestDto, findMember);
        } else {
            Organization o = createAndSaveOrganizationWithMember(requestDto, findMember);

            CreateTeamRequestDto request = CreateTeamRequestDto.builder()
                    .organizationId(o.getId())
                    .teamName("MY TEAM")
                    .build();
            teamService.createTeam(request, memberId);
        }
    }

    private Organization createAndSaveOrganizationWithMember(CreateOrganizationRequestDto requestDto, Member findMember) {
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

        return o;
    }

    @Transactional
    public List<GetOrganizationsResponseDto> getOrganizations(Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        List<MemberOrganization> organizations = memberOrganizationRepository.findAllByMember(findMember);
        return organizations.stream().map(org -> new GetOrganizationsResponseDto(org.getOrganization().getId(), org.getOrganization().getName(), org.getOrganization().getTeamCount())).collect(Collectors.toList());
    }

    @Transactional
    public void deleteOrganization(DeleteOrganizationRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization findOrganization = organizationRepository.findById(requestDto.getOrganizationId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        if (findOrganization.getHeader().equals(member)) {
            throw new BusinessException(ExceptionCode.ORGANIZATION_UNAUTHORIZED);
        }

        // 개인 고유 조직은 조직 삭제 불가
        if (isMyOrganization(findOrganization.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        List<Team> fidnTeamList = teamRepository.findAllByOrganization(findOrganization);
        teamRepository.deleteAll(fidnTeamList);
        organizationRepository.delete(findOrganization);
    }

    @Transactional
    public void inviteMemberToOrganization(InviteMemberToOrganizationRequestDto requestDto, Long memberId) {
        Member sender = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization findOrganization = organizationRepository.findById(requestDto.getOrganizationId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        // 개인 고유 조직은 조직원 초대 불가
        if (isMyOrganization(findOrganization.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }
        /**
         * Todo 권한 확인
         */

        invitationService.inviteMemberToOrganization(findOrganization, sender, requestDto.getEmail());

        Member receiver = memberRepository.findByEmail(requestDto.getEmail());
        if (receiver != null) {
            notificationService.notify(sender, receiver, "팀 초대", "팀 초대 메시지", NotificationType.USER, null);
        }
    }

    @Transactional
    public void removeMemberFromOrganization(RemoveMemberFromOrganizationRequestDto requestDto, Long memberId) {
        Organization findOrganization = organizationRepository.findById(requestDto.getOrganizationId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Member removeMember = memberRepository.findByEmail(requestDto.getEmail());

        // 개인 고유 조직은 조직원 추방 불가
        if (isMyOrganization(findOrganization.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

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

    @Transactional
    public void leaveOrganization(LeaveOrganizationRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        // 개인 고유 조직은 조직 탈퇴 불가
        if (isMyOrganization(organization.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 조직장은 떠나기가 없음, 조직해체만 가능
        if (organization.getHeader().equals(member)) {
            throw new BusinessException(ExceptionCode.ORGANIZATION_UNAUTHORIZED);
        }
        List<Team> teamList = teamRepository.findAllByOrganization(organization);

        for (Team team : teamList) {
            MemberTeam findMemberAndTeam = memberTeamRepository.findByMemberAndTeam(member, team);
            MemberRole findMemberAndRole = memberRoleRepository.findByMemberAndTeam(member, team);
            if (findMemberAndTeam != null) {
                memberTeamRepository.delete(findMemberAndTeam);
            }

            if (findMemberAndRole != null) {
                memberRoleRepository.delete(findMemberAndRole);
            }
        }
        MemberOrganization findMemberAndOrganization = memberOrganizationRepository.findByMemberAndOrganization(member, organization);
        memberOrganizationRepository.delete(findMemberAndOrganization);
    }

    @Transactional
    public void renameOrganization(RenameOrganizationRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        // 개인 고유 조직은 조직명 변경 불가
        if (isMyOrganization(organization.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 조직명 변경은 조직장만 가능
        if (!organization.getHeader().equals(member)) {
            throw new BusinessException(ExceptionCode.ORGANIZATION_UNAUTHORIZED);
        }

        if (organization.getName().equals(requestDto.getNewName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        organization.setName(requestDto.getNewName());
        organizationRepository.save(organization);
    }

    @Transactional
    public void assignNewHeader(AssignNewHeaderRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Member newHeader = memberRepository.findById(requestDto.getNewHeaderId()).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        // 개인 고유 조직은 조직장 위임 불가
        if (isMyOrganization(organization.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 조직장 위임은 조직장만 가능
        if (!organization.getHeader().equals(member)) {
            throw new BusinessException(ExceptionCode.ORGANIZATION_UNAUTHORIZED);
        }

        MemberOrganization findMemberAndOrganization = memberOrganizationRepository.findByMemberAndOrganization(newHeader, organization);
        // 조직장 위임은 현재 우리 조직원에게만 가능
        if (findMemberAndOrganization == null) {
            throw new BusinessException(ExceptionCode.ORGANIZATION_MEMBER_NOT_FOUND);
        }

        // 기존 조직장은 모든 팀에서 "GUEST"로 변경
        // 새로운 조직장은 모든 팀에서 "HEADER"로 변경
        List<Team> teamList = teamRepository.findAllByOrganization(organization);
        for (Team team : teamList) {
            Role guest = roleRepository.findByTeamAndName(team, "GUEST");
            Role header = roleRepository.findByTeamAndName(team, "HEADER");
            MemberRole findMemberAndRole = memberRoleRepository.findByMemberAndTeam(member, team);
            MemberRole findNewHeaderAndRole = memberRoleRepository.findByMemberAndTeam(newHeader, team);
            findMemberAndRole.setRole(guest);
            findNewHeaderAndRole.setRole(header);

            memberRoleRepository.save(findMemberAndRole);
            memberRoleRepository.save(findNewHeaderAndRole);
        }

    }

    private boolean isMyOrganization(String organizationName) {
        return "MY ORGANIZATION".equals(organizationName);
    }
}
