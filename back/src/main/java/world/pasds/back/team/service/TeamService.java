package world.pasds.back.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.authority.entity.Authority;
import world.pasds.back.authority.entity.AuthorityName;
import world.pasds.back.authority.repository.AuthorityRepository;
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
import world.pasds.back.organization.repository.OrganizationRepository;
import world.pasds.back.role.entity.Role;
import world.pasds.back.role.entity.RoleAuthority;
import world.pasds.back.role.repository.RoleAuthorityRepository;
import world.pasds.back.role.repository.RoleRepository;
import world.pasds.back.team.entity.Team;
import world.pasds.back.team.entity.dto.request.*;
import world.pasds.back.team.entity.dto.response.GetTeamsResponseDto;
import world.pasds.back.team.repository.TeamRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final MemberRepository memberRepository;
    private final MemberTeamRepository memberTeamRepository;
    private final MemberOrganizationRepository memberOrganizationRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final TeamRepository teamRepository;
    private final OrganizationRepository organizationRepository;
    private final InvitationService invitationService;
    private final RoleRepository roleRepository;
    private final RoleAuthorityRepository roleAuthorityRepository;
    private final AuthorityRepository authorityRepository;
    private final NotificationService notificationService;

    @Transactional
    public List<GetTeamsResponseDto> getTeams(GetTeamsRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        List<Team> findTeamList = teamRepository.findAllByOrganization(organization);

        List<GetTeamsResponseDto> response = new ArrayList<>();
        for (Team team : findTeamList) {
            MemberTeam findMemberAndTeam = memberTeamRepository.findByMemberAndTeam(member, team);

            // 내가 속해 있는 팀
            if (findMemberAndTeam != null) {
                response.add(new GetTeamsResponseDto(team.getId(), organization.getId(), team.getName()));
            }
        }

        return response;
    }

    @Transactional
    public void createTeam(CreateTeamRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        Member header = organization.getHeader();

        if (teamRepository.existsByOrganizationAndName(organization, requestDto.getTeamName())) {
            throw new BusinessException(ExceptionCode.TEAM_NAME_CONFLICT);
        }

        /**
         * Todo 팀 비밀키 발급
         */
        byte[] encryptedDataKey = null;
        byte[] encryptedIv = null;
        LocalDateTime expiredAt = null;

        // 팀 생성
        Team newTeam = Team.builder()
                .header(member)
                .organization(organization)
                .name(requestDto.getTeamName())
                .roleCount(2)
                .secretCount(0)
                .encryptedDataKey(encryptedDataKey)
                .encryptedIv(encryptedIv)
                .expiredAt(expiredAt)
                .build();
        Team savedTeam = teamRepository.save(newTeam);

        // 멤버_팀 추가
        MemberTeam memberTeam = MemberTeam.builder()
                .member(member)
                .team(savedTeam)
                .build();
        memberTeamRepository.save(memberTeam);

        // 기본 역할 추가
        Role headerRole = Role.builder()
                .team(savedTeam)
                .name("HEADER")
                .build();
        Role guestRole = Role.builder()
                .team(savedTeam)
                .name("GUEST")
                .build();
        Role savedRole = roleRepository.save(headerRole);
        roleRepository.save(guestRole);

        // 팀장에 모든 권한 부여
        List<Authority> authorityList = authorityRepository.findAll();
        List<RoleAuthority> roleAuthorityList = authorityList.stream().map(authority -> RoleAuthority.builder()
                .role(savedRole)
                .authority(authority)
                .build()).collect(Collectors.toList());
        roleAuthorityRepository.saveAll(roleAuthorityList);

        // 생성자에게 팀장 역할 부여
        MemberRole memberRole = MemberRole.builder()
                .role(savedRole)
                .member(member)
                .team(savedTeam)
                .build();
        memberRoleRepository.save(memberRole);

        // 조직장도 팀장 권한 부여
        if (!member.equals(header)) {
            // 조직장도 멤버_팀 추가
            MemberTeam orgHeaderTeam = MemberTeam.builder()
                    .member(header)
                    .team(savedTeam)
                    .build();
            memberTeamRepository.save(orgHeaderTeam);

            // 조직장에게 팀장 권한 부여
            MemberRole orgHeaderRole = MemberRole.builder()
                    .role(savedRole)
                    .member(header)
                    .team(savedTeam)
                    .build();
            memberRoleRepository.save(orgHeaderRole);
        }
    }

    @Transactional
    public void deleteTeam(DeleteTeamRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        // 권한 확인 - 조직장 혹은 팀장
        Role role = roleRepository.findByTeamAndName(team, "HEADER");
        MemberRole findMemberAndRole = memberRoleRepository.findByMemberAndRole(member, role);

        if (findMemberAndRole == null) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }
        teamRepository.delete(team);
    }

    @Transactional
    public void inviteMemberToTeam(InviteMemberToTeamRequestDto requestDto, Long memberId) {
        Member sender = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Member receiver = memberRepository.findByEmail(requestDto.getInviteMemberEmail());
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        // 팀 초대권한 확인
        Authority inviteAuthority = authorityRepository.findByName(AuthorityName.TEAM_INVITE);
        MemberRole memberRole = memberRoleRepository.findByMemberAndTeam(sender, team);
        Role role = memberRole.getRole();
        RoleAuthority findRoleAndAuthority = roleAuthorityRepository.findByRoleAndAuthority(role, inviteAuthority);
        if (findRoleAndAuthority == null) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        // 우리 회원인 경우
        if (receiver != null) {
            MemberOrganization findMemberAndOrganization = memberOrganizationRepository.findByMemberAndOrganization(receiver, organization);
            if (findMemberAndOrganization != null) { // 이미 우리 조직인 경우
                MemberTeam findMemberAndTeam = memberTeamRepository.findByMemberAndTeam(receiver, team);
                if (findMemberAndTeam != null) {    // 이미 우리 팀인 경우
                    throw new BusinessException(ExceptionCode.TEAM_MEMBER_EXISTS);
                }
            }
            invitationService.inviteMemberToTeam(organization, team, sender, requestDto.getInviteMemberEmail());
            notificationService.notify(sender, receiver, "팀 초대", "팀 초대", NotificationType.USER, null);
        } else {
            invitationService.inviteMemberToTeam(organization, team, sender, requestDto.getInviteMemberEmail());
        }
    }

    @Transactional
    public void removeMemberFromTeam(RemoveMemberFromTeamRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Member removeMember = memberRepository.findByEmail(requestDto.getRemoveMemberEmail());
        if (removeMember == null) {
            throw new BusinessException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        // 팀 추방권한 확인
        Authority inviteAuthority = authorityRepository.findByName(AuthorityName.TEAM_REMOVE);
        MemberRole memberRole = memberRoleRepository.findByMemberAndTeam(member, team);
        Role role = memberRole.getRole();
        RoleAuthority findRoleAndAuthority = roleAuthorityRepository.findByRoleAndAuthority(role, inviteAuthority);
        if (findRoleAndAuthority == null) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        // 추방할 사람이 팀원인지 확인
        MemberTeam findMemberAndTeam = memberTeamRepository.findByMemberAndTeam(removeMember, team);
        if (findMemberAndTeam == null) {
            throw new BusinessException(ExceptionCode.TEAM_MEMBER_NOT_FOUND);
        }
        memberTeamRepository.delete(findMemberAndTeam);
        MemberRole removeMemberRole = memberRoleRepository.findByMemberAndTeam(removeMember, team);
        memberRoleRepository.delete(removeMemberRole);
    }

    @Transactional
    public void leaveTeam(LeaveTeamRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        // 팀장은 떠나기가 없음, 팀해체만 가능
        if (team.getHeader().equals(member)) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        // 팀원인지 확인
        MemberTeam findMemberAndTeam = memberTeamRepository.findByMemberAndTeam(member, team);
        if (findMemberAndTeam == null) {
            throw new BusinessException(ExceptionCode.TEAM_MEMBER_NOT_FOUND);
        }
        memberTeamRepository.delete(findMemberAndTeam);
        MemberRole removeMemberRole = memberRoleRepository.findByMemberAndTeam(member, team);
        memberRoleRepository.delete(removeMemberRole);
    }

    @Transactional
    public void assignNewHeader(AssignNewTeamHeaderRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Member newHeader = memberRepository.findById(requestDto.getNewHeaderId()).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        // 팀장 위임은 팀장만 가능
        if (!team.getHeader().equals(member)) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        MemberTeam findMemberAndTeam = memberTeamRepository.findByMemberAndTeam(newHeader, team);
        // 팀장 위임은 현재 우리 팀원에게만 가능
        if (findMemberAndTeam == null) {
            throw new BusinessException(ExceptionCode.TEAM_MEMBER_NOT_FOUND);
        }
        Role guest = roleRepository.findByTeamAndName(team, "GUEST");
        Role header = roleRepository.findByTeamAndName(team, "HEADER");
        MemberRole findMemberAndRole = memberRoleRepository.findByMemberAndTeam(member, team);
        MemberRole findNewHeaderAndRole = memberRoleRepository.findByMemberAndTeam(newHeader, team);
        findMemberAndRole.setRole(guest);
        findNewHeaderAndRole.setRole(header);

        memberRoleRepository.save(findMemberAndRole);
        memberRoleRepository.save(findNewHeaderAndRole);
    }

    @Transactional
    public void renameTeam(RenameTeamRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        // 팀명 변경은 팀장만 가능
        if (!team.getHeader().equals(member)) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        List<Team> teamList = teamRepository.findAllByOrganization(team.getOrganization());
        for (Team t : teamList) {
            if (t.getName().equals(requestDto.getNewName())) {
                throw new BusinessException(ExceptionCode.TEAM_NAME_CONFLICT);
            }
        }

        if (team.getName().equals(requestDto.getNewName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        team.setName(requestDto.getNewName());
        teamRepository.save(team);
    }
}
