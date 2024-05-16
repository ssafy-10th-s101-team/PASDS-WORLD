package world.pasds.back.organization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.dashboard.entity.OrganizationDashboard;
import world.pasds.back.dashboard.repository.OrganizationDashboardRepository;
import world.pasds.back.invitaion.service.InvitationService;
import world.pasds.back.member.entity.*;
import world.pasds.back.member.repository.MemberOrganizationRepository;
import world.pasds.back.member.repository.MemberRepository;
import world.pasds.back.member.repository.MemberRoleRepository;
import world.pasds.back.member.repository.MemberTeamRepository;
import world.pasds.back.notification.entity.NotificationType;
import world.pasds.back.notification.service.NotificationService;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.organization.entity.OrganizationRole;
import world.pasds.back.organization.entity.dto.request.*;
import world.pasds.back.organization.entity.dto.response.GetOrganizationMemberDto;
import world.pasds.back.organization.entity.dto.response.GetOrganizationMemberResponseDto;
import world.pasds.back.organization.entity.dto.response.GetOrganizationsResponseDto;
import world.pasds.back.organization.repository.OrganizationRepository;
import world.pasds.back.role.entity.Role;
import world.pasds.back.role.repository.RoleRepository;
import world.pasds.back.team.entity.Team;
import world.pasds.back.team.entity.dto.request.CreateTeamRequestDto;
import world.pasds.back.team.entity.dto.request.DeleteTeamRequestDto;
import world.pasds.back.team.entity.dto.response.GetTeamsResponseDto;
import world.pasds.back.team.repository.TeamRepository;
import world.pasds.back.team.service.TeamService;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final OrganizationDashboardRepository organizationDashboardRepository;

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

    @Transactional
    public GetOrganizationMemberResponseDto getOrganizationMember(Long organizationId, int offset, Long memberId) {
        Pageable pageable = PageRequest.of(offset - 1, 10);

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        //현재 멤버 권한있는지확인
        if (!memberOrganizationRepository.existsByMemberAndOrganization(member, organization)) {
            throw new BusinessException(ExceptionCode.ORGANIZATION_UNAUTHORIZED);
        }

        //팀찾는데, 조직안에있는 팀만 보여주기
        List<GetOrganizationMemberDto> response = new ArrayList<>();

        Page<MemberOrganization> memberOrganizations = memberOrganizationRepository.findAllByOrganization(organization, pageable);

        for(MemberOrganization memberOrganization  : memberOrganizations.getContent()){
            List<MemberTeam> memberTeams = memberTeamRepository.findByMemberIdAndOrganizationId(memberOrganization.getMember().getId(), organizationId);
            List<GetTeamsResponseDto> getTeamsResponseDtos = memberTeams
                    .stream()
                    .map(mt -> GetTeamsResponseDto
                            .builder()
                            .organizationId(organizationId)
                            .teamId(mt.getTeam().getId())
                            .teamName(mt.getTeam().getName())
                            .build())
                    .toList();

            response.add(GetOrganizationMemberDto
                    .builder()
                    .memberId(memberOrganization.getMember().getId())
                    .name(memberOrganization.getMember().getNickname())
                    .organizationRole(memberOrganization.getOrganizationRole())
                    .email(memberOrganization.getMember().getEmail())
                    .teams(getTeamsResponseDtos)
                    .build());
        }

        return GetOrganizationMemberResponseDto.builder()
                .totalPages(memberOrganizations.getTotalPages())
                .organizationMemberResponse(response)
                .build();
    }

    @Transactional
    public List<GetOrganizationsResponseDto> getOrganizations(Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        List<MemberOrganization> organizations = memberOrganizationRepository.findAllByMember(findMember);
        return organizations.stream().map(org -> new GetOrganizationsResponseDto(org.getOrganization().getId(), org.getOrganization().getName(), org.getOrganization().getTeamCount(), org.getOrganizationRole())).collect(Collectors.toList());
    }


    @Transactional
    public void deleteOrganization(DeleteOrganizationRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization findOrganization = organizationRepository.findById(requestDto.getOrganizationId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        // 조직장만이 조직해체 가능
        if (!findOrganization.getHeader().getId().equals(member.getId())) {
            throw new BusinessException(ExceptionCode.ORGANIZATION_UNAUTHORIZED);
        }

        // 개인 고유 조직은 조직 삭제 불가
        if (isMyOrganization(findOrganization.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 조직의 모든 팀 삭제
        List<Team> findTeamList = teamRepository.findAllByOrganization(findOrganization);
        for (Team team : findTeamList) {
            teamService.deleteTeam(new DeleteTeamRequestDto(team.getId()), memberId);
        }
        teamRepository.deleteAll(findTeamList);

        List<MemberOrganization> memberOrganizationList = memberOrganizationRepository.findAllByOrganization(findOrganization);
        memberOrganizationRepository.deleteAll(memberOrganizationList);
        List<OrganizationDashboard> organizationDashboardList = organizationDashboardRepository.findAllByOrganization(findOrganization);
        organizationDashboardRepository.deleteAll(organizationDashboardList);
        organizationRepository.delete(findOrganization);
    }

    @Transactional
    public void inviteMemberToOrganization(InviteMemberToOrganizationRequestDto requestDto, Long memberId) {
        Member sender = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization findOrganization = organizationRepository.findById(requestDto.getOrganizationId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        // 개인 고유 조직은 조직원 초대 불가
        if (isMyOrganization(findOrganization.getName())) {
            throw new BusinessException(ExceptionCode.MY_ORGANIZATION_INVITATION);
        }

        MemberOrganization findMO = memberOrganizationRepository.findByMemberAndOrganization(sender, findOrganization);

        // 조직의 일반 회원은 조직원 초대 불가
        if (OrganizationRole.MEMBER == findMO.getOrganizationRole()) {
            throw new BusinessException(ExceptionCode.ORGANIZATION_UNAUTHORIZED);
        }

        // 역할 부여시 HEADER 역할 부여는 불가능
        if (OrganizationRole.HEADER == requestDto.getOrganizationRole()) {
            throw new BusinessException(ExceptionCode.NO_HEADER);
        }

        Member receiver = memberRepository.findByEmail(requestDto.getEmail());
        if (receiver != null) { // 우리 회원인 경우 알림 전송

            //우리회원인데 이미 조직원인경우 보낼수 없음.
            MemberOrganization receiverMO =  memberOrganizationRepository.findByMemberAndOrganization(receiver, findOrganization);
            if(receiverMO != null){
                throw new BusinessException(ExceptionCode.ALREADY_ORGANIZATION_MEMBER);
            }
            /**
             * TODO: 알림 설정
             */
            notificationService.notify(sender, receiver, "조직 초대", "조직 초대 메시지", NotificationType.USER, null);
        }

        // 조직원 초대시 조직에서의 역할 부여
        invitationService.inviteMemberToOrganization(findOrganization, sender, requestDto.getOrganizationRole(), requestDto.getEmail());
    }

    @Transactional
    public void removeMemberFromOrganization(RemoveMemberFromOrganizationRequestDto requestDto, Long memberId) {
        Organization findOrganization = organizationRepository.findById(requestDto.getOrganizationId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Member removeMember = memberRepository.findByEmail(requestDto.getEmail());

        // 개인 고유 조직은 조직원 추방 불가
        if (isMyOrganization(findOrganization.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 우리 회원이 아닌 경우
        if (removeMember == null) {
            throw new BusinessException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        // 우리 조직원이 아닌 경우
        MemberOrganization findMemberAndOrganization = memberOrganizationRepository.findByMemberAndOrganization(removeMember, findOrganization);
        if (findMemberAndOrganization == null) {
            throw new BusinessException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        // 조직내의 MEMBER 역할은 조직원 추방 불가능
        MemberOrganization findMemberOrganization = memberOrganizationRepository.findByMemberAndOrganization(member, findOrganization);
        if (OrganizationRole.MEMBER == findMemberOrganization.getOrganizationRole()) {
            throw new BusinessException(ExceptionCode.ORGANIZATION_UNAUTHORIZED);
        }

        // 모든 팀에서 추방
        List<Team> findTeamList = teamRepository.findAllByOrganization(findOrganization);
        List<MemberTeam> removeMemberTeamList = new ArrayList<>();
        List<MemberRole> removeMemberRoleList = new ArrayList<>();
        for (Team team : findTeamList) {
            MemberTeam findTeamMember = memberTeamRepository.findByMemberAndTeam(removeMember, team);
            MemberRole findMemberRole = memberRoleRepository.findByMemberAndTeam(removeMember, team);
            if (findTeamMember != null) {
                removeMemberTeamList.add(findTeamMember);
            }
            if (findTeamMember != null) {
                removeMemberRoleList.add(findMemberRole);
            }
        }
        memberTeamRepository.deleteAll(removeMemberTeamList);
        memberRoleRepository.deleteAll(removeMemberRoleList);

        //조직에서 삭제
        memberOrganizationRepository.delete(findMemberAndOrganization);
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
        if (organization.getHeader().getId().equals(member.getId())) {
            throw new BusinessException(ExceptionCode.ORGANIZATION_UNAUTHORIZED);
        }

        // 조직 내 모든 팀에서 탈퇴
        List<Team> teamList = teamRepository.findAllByOrganization(organization);
        List<MemberTeam> removeMemberTeamList = new ArrayList<>();
        List<MemberRole> removeMemberRoleList = new ArrayList<>();
        for (Team team : teamList) {
            MemberTeam findMemberTeam = memberTeamRepository.findByMemberAndTeam(member, team);
            MemberRole findMemberRole = memberRoleRepository.findByMemberAndTeam(member, team);
            if (findMemberTeam != null) {
                removeMemberTeamList.add(findMemberTeam);
            }

            if (findMemberRole != null) {
                removeMemberRoleList.add(findMemberRole);
            }
        }
        memberTeamRepository.deleteAll(removeMemberTeamList);
        memberRoleRepository.deleteAll(removeMemberRoleList);

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
        if (!organization.getHeader().getId().equals(member.getId())) {
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
        if (!organization.getHeader().getId().equals(member.getId())) {
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
        List<MemberRole> memberRoleList = new ArrayList<>();
        for (Team team : teamList) {
            Role guest = roleRepository.findByTeamAndName(team, "GUEST");
            Role header = roleRepository.findByTeamAndName(team, "HEADER");
            MemberRole findMemberAndRole = memberRoleRepository.findByMemberAndTeam(member, team);
            MemberRole findNewHeaderAndRole = memberRoleRepository.findByMemberAndTeam(newHeader, team);
            findMemberAndRole.setRole(guest);
            findNewHeaderAndRole.setRole(header);

            memberRoleList.add(findMemberAndRole);
            memberRoleList.add(findNewHeaderAndRole);
        }
        memberRoleRepository.saveAll(memberRoleList);

        // 조직장 위임
        MemberOrganization findMemberOrganization = memberOrganizationRepository.findByMemberAndOrganization(member, organization);
        findMemberOrganization.setOrganizationRole(OrganizationRole.MEMBER);
        findMemberAndOrganization.setOrganizationRole(OrganizationRole.HEADER);
        memberOrganizationRepository.save(findMemberOrganization);
        memberOrganizationRepository.save(findMemberAndOrganization);

        organization.setHeader(newHeader);
        organizationRepository.save(organization);
    }

    @Transactional
    public void updateRole(UpdateRoleRequestDto requestDto, Long memberId) {
        Member requestMember = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Member member = memberRepository.findById(requestDto.getOrganizationMemberId()).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        MemberOrganization requestMemberOrganization = memberOrganizationRepository.findByMemberAndOrganization(requestMember, organization);
        MemberOrganization memberOrganization = memberOrganizationRepository.findByMemberAndOrganization(member,organization);

        // 두 명 모두 조직원이어야 함
        if (requestMemberOrganization == null || memberOrganization == null) {
            throw new BusinessException(ExceptionCode.ORGANIZATION_MEMBER_NOT_FOUND);
        }

        OrganizationRole newOrganizationRole = requestDto.getNewOrganizationRole();

        // 역할 수정가능한지 권한 확인
        if (requestMemberOrganization.getOrganizationRole() == OrganizationRole.MEMBER) {
            throw new BusinessException(ExceptionCode.ORGANIZATION_UNAUTHORIZED);
        }

        // 조직장으로 조직 내 역할 수정 불가
        if (newOrganizationRole == OrganizationRole.HEADER) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 조직장 역할 수정 불가
        if (memberOrganization.getOrganizationRole() == OrganizationRole.HEADER) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 자기 자신 역할 수정 불가
        if (member.getId().equals(requestMember.getId())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        memberOrganization.setOrganizationRole(newOrganizationRole);
        memberOrganizationRepository.save(memberOrganization);
    }

    private boolean isMyOrganization(String organizationName) {
        return "MY ORGANIZATION".equals(organizationName);
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
                .organizationRole(OrganizationRole.HEADER)
                .build();
        memberOrganizationRepository.save(mo);

        return o;
    }

}
