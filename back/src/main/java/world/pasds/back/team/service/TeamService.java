package world.pasds.back.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.authority.entity.Authority;
import world.pasds.back.authority.entity.AuthorityName;
import world.pasds.back.authority.repository.AuthorityRepository;
import world.pasds.back.common.dto.KmsEncryptionKeysResponseDto;
import world.pasds.back.common.dto.KmsKeyDto;
import world.pasds.back.common.dto.KmsReGenerationKeysResponseDto;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.common.service.KeyService;
import world.pasds.back.dashboard.service.OrganizationDashboardService;
import world.pasds.back.dashboard.service.TeamDashboardService;
import world.pasds.back.invitaion.service.InvitationService;
import world.pasds.back.member.entity.*;
import world.pasds.back.member.repository.MemberOrganizationRepository;
import world.pasds.back.member.repository.MemberRepository;
import world.pasds.back.member.repository.MemberRoleRepository;
import world.pasds.back.member.repository.MemberTeamRepository;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.organization.entity.OrganizationRole;
import world.pasds.back.organization.repository.OrganizationRepository;
import world.pasds.back.privateData.entity.PrivateData;
import world.pasds.back.privateData.repository.jpa.PrivateDataRepository;
import world.pasds.back.role.entity.Role;
import world.pasds.back.role.entity.RoleAuthority;
import world.pasds.back.role.repository.RoleAuthorityRepository;
import world.pasds.back.role.repository.RoleRepository;
import world.pasds.back.team.entity.Team;
import world.pasds.back.team.entity.dto.request.*;
import world.pasds.back.team.entity.dto.response.*;
import world.pasds.back.team.repository.TeamRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final MemberRepository memberRepository;
    private final MemberTeamRepository memberTeamRepository;
    private final MemberOrganizationRepository memberOrganizationRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final TeamRepository teamRepository;
    private final OrganizationRepository organizationRepository;
    private final InvitationService invitationService;
    private final KeyService keyService;
    private final RoleRepository roleRepository;
    private final RoleAuthorityRepository roleAuthorityRepository;
    private final AuthorityRepository authorityRepository;
    private final PrivateDataRepository privateDataRepository;
    private final OrganizationDashboardService organizationDashboardService;
    private final TeamDashboardService teamDashboardService;

    @Transactional
    public List<GetTeamsResponseDto> getTeams(Long organizationId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        List<Team> findTeamList = teamRepository.findAllByOrganization(organization);

        List<GetTeamsResponseDto> response = new ArrayList<>();
        for (Team team : findTeamList) {
            MemberTeam findMemberAndTeam = memberTeamRepository.findByMemberAndTeam(member, team);

            // 내가 속해 있는 팀
            if (findMemberAndTeam != null) {
                response.add(new GetTeamsResponseDto(organization.getId(), team.getId(), team.getName()));
            }
        }

        return response;
    }

    @Transactional
    public GetTeamLeaderResponseDto getTeamLeader(Long teamId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        Member leader = team.getLeader();
        return GetTeamLeaderResponseDto.builder().id(leader == null ? null : leader.getId()).nickname(leader == null ? null : leader.getNickname()).build();
    }

    @Transactional
    public List<GetAdminTeamsResponseDto> getAdminTeams(Long organizationId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        //권한 체크
        List<OrganizationRole> roles = Arrays.asList(OrganizationRole.HEADER, OrganizationRole.ADMIN);
        boolean authorized = memberOrganizationRepository.existsByMemberAndOrganizationAndOrganizationRoleIn(member, organization, roles);
        if (!authorized) throw new BusinessException(ExceptionCode.ORGANIZATION_UNAUTHORIZED);

        //모든 팀목록 조회
        List<Team> findTeamList = teamRepository.findAllByOrganization(organization);

        List<GetAdminTeamsResponseDto> response = new ArrayList<>();


        for (Team team : findTeamList) {
            //내가 맡은 역할 찾기
            MemberRole memberRole = memberRoleRepository.findByMemberAndTeam(member, team);
            String roleName  = null;
            if (memberRole != null)
                roleName = memberRole.getRole().getName();

            response.add(new GetAdminTeamsResponseDto(organization.getId(), team.getId(), team.getName(), roleName, team.getSecretCount()));
        }

        return response;
    }


    @Transactional
    public GetTeamMemberResponseDto getTeamMember(Long teamId, int offset, Long memberId) {
        Pageable pageable = PageRequest.of(offset - 1, 10);
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        if (!memberTeamRepository.existsByMemberAndTeam(member, team)) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        Page<MemberRole> memberRoleList = memberRoleRepository.findAllByTeam(team, pageable);
        List<GetTeamMemberDto> response = new ArrayList<>();
        for (MemberRole memberRole : memberRoleList.getContent()) {
            response.add(GetTeamMemberDto.builder()
                    .id(memberRole.getMember().getId())
                    .memberNickname(memberRole.getMember().getNickname())
                    .role(memberRole.getRole().getName())
                    .build());
        }

        return GetTeamMemberResponseDto.builder()
                .totalPages(memberRoleList.getTotalPages())
                .teamMemberResponse(response)
                .build();
    }

    @Transactional
    public void createTeam(CreateTeamRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        Member header = organization.getHeader();

        // 개인 고유 조직에서 생성한 팀이 아닌 경우
        if (!"MY ORGANIZATION".equals(organization.getName())) {
            // 개개인의 고유 팀명인 "MY TEAM" 으로 팀 생성 불가
            if (isMyTeam(requestDto.getTeamName())) {
                throw new BusinessException(ExceptionCode.BAD_REQUEST);
            }
        }

        // 조직 내에 이미 같은 팀명 생성 불가
        if (teamRepository.existsByOrganizationAndName(organization, requestDto.getTeamName())) {
            throw new BusinessException(ExceptionCode.TEAM_NAME_CONFLICT);
        }

        //Data key 발급
        KmsEncryptionKeysResponseDto encryptionKeys = keyService.generateKeys();
        byte[] encryptedDataKey = Base64.getDecoder().decode(encryptionKeys.getEncryptedDataKey());
        byte[] encryptedIv = Base64.getDecoder().decode(encryptionKeys.getEncryptedIv());
        LocalDateTime expiredAt = LocalDateTime.now().plusDays(90);

        Team savedTeam;
        // 조직장이 팀 생성시
        if (header.getId().equals(member.getId())) {
            Team newTeam = Team.builder()
                    .leader(null)
                    .organization(organization)
                    .name(requestDto.getTeamName())
                    .roleCount(3)
                    .secretCount(0)
                    .encryptedDataKey(encryptedDataKey)
                    .encryptedIv(encryptedIv)
                    .expiredAt(expiredAt)
                    .build();
            savedTeam = teamRepository.save(newTeam);
        } else {
            Team newTeam = Team.builder()
                    .leader(member)
                    .organization(organization)
                    .name(requestDto.getTeamName())
                    .roleCount(3)
                    .secretCount(0)
                    .encryptedDataKey(encryptedDataKey)
                    .encryptedIv(encryptedIv)
                    .expiredAt(expiredAt)
                    .build();
            savedTeam = teamRepository.save(newTeam);
        }

        // 기본 역할(조직장, 팀장, 손님) 추가
        Role headerRole = roleRepository.save(Role.builder()
                .team(savedTeam)
                .name("HEADER")
                .build());
        Role leaderRole = roleRepository.save(Role.builder()
                .team(savedTeam)
                .name("LEADER")
                .build());
        Role guestRole = roleRepository.save(Role.builder()
                .team(savedTeam)
                .name("GUEST")
                .build());

        // 조직장, 팀장에 모든 권한 부여
        List<Authority> authorityList = authorityRepository.findAll();
        List<RoleAuthority> headerRoleAuthorityList = authorityList.stream()
                .map(authority -> RoleAuthority.builder()
                        .role(headerRole)
                        .authority(authority)
                        .build()).toList();
        List<RoleAuthority> leaderRoleAuthorityList = authorityList.stream()
                .map(authority -> RoleAuthority.builder()
                        .role(leaderRole)
                        .authority(authority)
                        .build()).toList();
        roleAuthorityRepository.saveAll(headerRoleAuthorityList);
        roleAuthorityRepository.saveAll(leaderRoleAuthorityList);

        // 팀 생성자 멤버_팀 추가
        MemberTeam memberTeam = MemberTeam.builder()
                .member(member)
                .team(savedTeam)
                .build();
        memberTeamRepository.save(memberTeam);

        // 조직장에게 조직장 권한 부여
        MemberRole orgHeaderRole = MemberRole.builder()
                .role(headerRole)
                .member(header)
                .team(savedTeam)
                .build();
        memberRoleRepository.save(orgHeaderRole);

        // 조직장이 아닌 사람이 팀을 생성하는 경우
        if (!header.getId().equals(member.getId())) {
            // 조직장도 멤버_팀 추가
            MemberTeam orgHeaderTeam = MemberTeam.builder()
                    .member(header)
                    .team(savedTeam)
                    .build();
            memberTeamRepository.save(orgHeaderTeam);

            // 생성자에게 팀장 역할 부여
            MemberRole memberRole = MemberRole.builder()
                    .role(leaderRole)
                    .member(member)
                    .team(savedTeam)
                    .build();
            memberRoleRepository.save(memberRole);
        }
    }

    @Transactional
    public void deleteTeam(DeleteTeamRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        // 고유 팀은 팀 삭제 불가
        if (isMyTeam(team.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 권한 확인 - 조직장 혹은 팀장
        if (member.getId().equals(team.getOrganization().getHeader().getId()) || member.getId().equals(team.getLeader().getId())) {
            List<Role> roleList = roleRepository.findAllByTeam(team);
            for (Role role : roleList) {
                List<MemberRole> memberRoleList = memberRoleRepository.findAllByRole(role);
                memberRoleRepository.deleteAll(memberRoleList);
                List<RoleAuthority> roleAuthorityList = roleAuthorityRepository.findAllByRole(role);
                roleAuthorityRepository.deleteAll(roleAuthorityList);
            }
            roleRepository.deleteAll(roleList);
            List<MemberTeam> memberTeamList = memberTeamRepository.findAllByTeam(team);
            memberTeamRepository.deleteAll(memberTeamList);
            teamRepository.delete(team);
        } else {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }
    }

    @Transactional
    public void inviteMemberToTeam(InviteMemberToTeamRequestDto requestDto, Long memberId) {
        Member sender = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Member receiver = memberRepository.findByEmail(requestDto.getInviteMemberEmail());
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        // 고유 팀은 팀원 초대 불가
        if (isMyTeam(team.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 팀 초대권한 확인
        Authority inviteAuthority = authorityRepository.findByName(AuthorityName.TEAM_INVITE);
        MemberRole memberRole = memberRoleRepository.findByMemberAndTeam(sender, team);
        Role role = memberRole.getRole();
        RoleAuthority findRoleAndAuthority = roleAuthorityRepository.findByRoleAndAuthority(role, inviteAuthority);
        if (findRoleAndAuthority == null) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        // 우리 회원 + 우리 조직원인 경우만 팀 초대 가능
        if (receiver != null) {
            MemberOrganization findMemberAndOrganization = memberOrganizationRepository.findByMemberAndOrganization(receiver, organization);
            if (findMemberAndOrganization != null) { // 우리 조직원인 경우만 팀 초대 가능
                MemberTeam findMemberAndTeam = memberTeamRepository.findByMemberAndTeam(receiver, team);
                if (findMemberAndTeam != null) {    // 이미 우리 팀인 경우
                    throw new BusinessException(ExceptionCode.TEAM_MEMBER_EXISTS);
                }
                /**
                 * Todo: 알림 url 설정
                 */
                Role receiverRole = roleRepository.findById(requestDto.getRoleId()).orElseThrow(() -> new BusinessException(ExceptionCode.ROLE_NOT_FOUND));
                invitationService.inviteMemberToTeam(organization, team, sender, receiver, receiverRole);
            }
        }
    }

    @Transactional
    public void removeMemberFromTeam(RemoveMemberFromTeamRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Member removeMember = memberRepository.findById(requestDto.getMemberId()).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));

        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        // 고유 팀은 팀원 추방 불가
        if (isMyTeam(team.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 조직장 추방 불가
        if (removeMember.getId().equals(team.getOrganization().getHeader().getId())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 팀장 추방 불가
        if (removeMember.getId().equals(team.getLeader().getId())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 팀 추방권한 확인
        Authority removeAuthority = authorityRepository.findByName(AuthorityName.TEAM_REMOVE);
        MemberRole memberRole = memberRoleRepository.findByMemberAndTeam(member, team);
        Role role = memberRole.getRole();
        RoleAuthority findRoleAndAuthority = roleAuthorityRepository.findByRoleAndAuthority(role, removeAuthority);
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

        // 고유 팀은 팀 탈퇴 불가
        if (isMyTeam(team.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 조직장은 떠나기가 없음, 팀해체만 가능
        if (member.getId().equals(team.getOrganization().getHeader().getId())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 팀장은 떠나기가 없음, 팀해체만 가능
        if (member.getId().equals(team.getLeader().getId())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
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
    public void assignNewLeader(AssignNewTeamHeaderRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Member newLeader = memberRepository.findById(requestDto.getNewLeaderId()).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        Member header = team.getOrganization().getHeader();
        Member leader = team.getLeader();
        // 고유 팀은 팀장 위임 불가
        if (isMyTeam(team.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 팀장 위임은 조직장과 팀장만 가능
        if (!(member.getId().equals(header.getId()) || member.getId().equals(leader.getId()))) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        MemberTeam findMemberAndTeam = memberTeamRepository.findByMemberAndTeam(newLeader, team);
        // 팀장 위임은 현재 우리 팀원에게만 가능
        if (findMemberAndTeam == null) {
            throw new BusinessException(ExceptionCode.TEAM_MEMBER_NOT_FOUND);
        }

        // 팀장 위임은 조직장에게 불가능
        if (newLeader.getId().equals(header.getId())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        Role leaderRole = roleRepository.findByTeamAndName(team, "LEADER");
        MemberRole findNewLeaderAndRole = memberRoleRepository.findByMemberAndTeam(newLeader, team);
        findNewLeaderAndRole.setRole(leaderRole);

        memberRoleRepository.save(findNewLeaderAndRole);

        // 팀장위임하는 사람이 조직장이 아닌경우
        if (!member.getId().equals(header.getId())) {
            Role guestRole = roleRepository.findByTeamAndName(team, "GUEST");
            MemberRole findMemberAndRole = memberRoleRepository.findByMemberAndTeam(member, team);
            findMemberAndRole.setRole(guestRole);

            memberRoleRepository.save(findMemberAndRole);
        }
    }

    @Transactional
    public void renameTeam(RenameTeamRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        // 고유 팀은 팀명변경 불가
        if (isMyTeam(team.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 팀명 변경은 조직장과 팀장만 가능
        if (!(member.getId().equals(team.getOrganization().getHeader().getId()) || member.getId().equals(team.getLeader().getId()))) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        // 조직 내 같은 팀명으로 변경 불가
        List<Team> teamList = teamRepository.findAllByOrganization(team.getOrganization());
        for (Team t : teamList) {
            if (t.getName().equals(requestDto.getNewName())) {
                throw new BusinessException(ExceptionCode.TEAM_NAME_CONFLICT);
            }
        }

        team.setName(requestDto.getNewName());
        teamRepository.save(team);
    }

    @Async
    @Transactional
    public void refreshByMasterKey() {

        //team 목록 가져오기..
        Long startId = 0L;
        Long endId = 1000L;
        while (true) {
            List<Team> teams = teamRepository.findByIdBetween(startId, endId);
            if (!teams.isEmpty()) {
                for (Team team : teams) {

                    //team에서 encryptedDataKey, encryptedIvKey 가져오기.
                    KmsKeyDto requestDto = KmsKeyDto.builder()
                            .encryptedDataKey(Base64.getEncoder().encodeToString(team.getEncryptedDataKey()))
                            .encryptedIv(Base64.getEncoder().encodeToString(team.getEncryptedIv()))
                            .build();

                    //data key 재암호화 요청.
                    KmsKeyDto responseDto = keyService.reEncrypt(requestDto);

                    //재암호화된 data key들 갱신
                    team.setEncryptedDataKey(Base64.getDecoder().decode(responseDto.getEncryptedDataKey()));
                    team.setEncryptedIv(Base64.getDecoder().decode(responseDto.getEncryptedIv()));
                    team.setExpiredAt(LocalDateTime.now().plusDays(90));
                    teamRepository.save(team);

                    //로그 찍기
                    log.info("member {}'s TeamDataKey re-encrypted", team.getId());
                }
                startId = endId;
                endId += 1000L;
            } else {
                break;
            }
        }
    }

    @Async
    @Transactional
    public void rotateAllDataKeys() {
        //team 목록 가져오기..
        Long startId = 0L;
        Long endId = 1000L;

        //팀 풀스캔. 1000개씩 검색.
        while (true) {
            List<Team> teams = teamRepository.findByIdBetween(startId, endId);
            if (!teams.isEmpty()) {
                for (Team team : teams) {

                    //만료여부확인
                    LocalDateTime expiredAt = team.getExpiredAt();
                    if (expiredAt.isAfter(LocalDateTime.now())) continue;

                    //만료시간이 지났으면 갱신로직 시작
                    changeTeamDataKey(team);

                    //조직 및 팀 키회전 증가
                    Long teamId = team.getId();
                    teamDashboardService.checkTeamDashboardDay(teamId);
                    teamDashboardService.upTeamDashBoard(teamId, 'r');
                    Long organizationId = getOrganizationId(teamId);
                    organizationDashboardService.checkOrganizationDashboardDay(organizationId);
                    organizationDashboardService.upOrganizationDashBoard(organizationId, 'r');

                }
                startId = endId;
                endId += 1000L;
            } else {
                break;
            }
        }
    }

    @Async
    @Transactional
    public void rotateDataKey(Long teamId, Long memberId) {
        //데이터 키 회전 요청은 팀장권한 가진사람만 가능.
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));
        MemberRole memberRole = memberRoleRepository.findByMemberAndTeam(member, team);
        Role role = memberRole.getRole();

        if ("LEADER".equals(role.getName()) || "HEADER".equals(role.getName())) {
            //팀 데이터 키 갱신.
            changeTeamDataKey(team);
            return;
        } else {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }
    }

    private void changeTeamDataKey(Team team) {
        //team에서 encryptedDataKey, encryptedDataIvKey 가져오기.
        KmsKeyDto requestDto = KmsKeyDto.builder()
                .encryptedDataKey(Base64.getEncoder().encodeToString(team.getEncryptedDataKey()))
                .encryptedIv(Base64.getEncoder().encodeToString(team.getEncryptedIv()))
                .build();

        //기존 데이터 키 복호화 및 재발급 요청
        KmsReGenerationKeysResponseDto responseDto = keyService.reGenerateKey(requestDto);

        //현재 팀에 해당하는 privateData 모두 가져오기
        List<PrivateData> privateDatas = privateDataRepository.findAllByTeam(team);
        for (PrivateData privateData : privateDatas) {
            //privatData 복호화
            byte[] plainContent = keyService.decryptSecret(privateData.getContent(),
                    Base64.getDecoder().decode(responseDto.getOldDataKey()),
                    Base64.getDecoder().decode(responseDto.getOldIv()));

            //재암호화
            byte[] encrpytedContent = keyService.encryptSecret(plainContent,
                    Base64.getDecoder().decode(responseDto.getNewDataKey()),
                    Base64.getDecoder().decode(responseDto.getNewIv()));

            //재암호화된 privateData 저장.
            privateData.setContent(encrpytedContent);
            privateDataRepository.save(privateData);
        }

        //재암호화된 data key들 갱신
        team.setEncryptedDataKey(Base64.getDecoder().decode(responseDto.getEncryptedNewDataKey()));
        team.setEncryptedIv(Base64.getDecoder().decode(responseDto.getEncryptedNewIv()));
        teamRepository.save(team);

        //로그 찍기
        log.info("team {}'s DataKey re-generated and all PrivateDate re-encrypted", team.getId());
    }

    private boolean isMyTeam(String teamName) {
        return "MY TEAM".equals(teamName);
    }

    public Long getOrganizationId(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        if (team.getOrganization() == null) {
            throw new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND);
        }

        return team.getOrganization().getId();
    }

}
