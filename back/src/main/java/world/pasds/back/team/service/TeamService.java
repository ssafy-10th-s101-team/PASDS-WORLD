package world.pasds.back.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import world.pasds.back.privateData.entity.PrivateData;
import world.pasds.back.role.entity.Role;
import world.pasds.back.role.entity.RoleAuthority;
import world.pasds.back.role.repository.RoleAuthorityRepository;
import world.pasds.back.role.repository.RoleRepository;
import world.pasds.back.team.entity.Team;
import world.pasds.back.team.entity.dto.request.*;
import world.pasds.back.team.entity.dto.response.GetTeamMemberResponseDto;
import world.pasds.back.team.entity.dto.response.GetTeamsResponseDto;
import world.pasds.back.team.repository.PrivateDataRepository;
import world.pasds.back.team.repository.TeamRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final NotificationService notificationService;

    private final PrivateDataRepository privateDataRepository;

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
    public List<GetTeamMemberResponseDto> getTeamMember(Long teamId, int offset, Long memberId) {
        Pageable pageable = PageRequest.of(offset, 10);
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        if (!memberTeamRepository.existsByMemberAndTeam(member, team)) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        List<MemberRole> memberRoleList = memberRoleRepository.findAllByTeam(team, pageable);
        List<GetTeamMemberResponseDto> response = new ArrayList<>();
        for (MemberRole memberRole : memberRoleList) {
            response.add(GetTeamMemberResponseDto.builder()
                    .memberNickname(memberRole.getMember().getNickname())
                    .role(memberRole.getRole().getName())
                    .build());
        }

        return response;
    }

    @Transactional
    public void createTeam(CreateTeamRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        Member header = organization.getHeader();

        // 팀 생성시 이름은 MY TEAM 불가능
        if ("MY TEAM".equals(requestDto.getTeamName())) {
            throw new BusinessException(ExceptionCode.TEAM_NAME_CONFLICT);
        }

        // 개인 고유 조직에서 생성한 팀이 아닌 경우
        if (!"MY ORGANIZATION".equals(organization.getName())) {
            // 개개인의 고유 팀명인 "MY TEAM" 으로 팀 생성 불가
            if (isMyTeam(requestDto.getTeamName())) {
                throw new BusinessException(ExceptionCode.BAD_REQUEST);
            }
        }

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
        if (header.equals(member)) {
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
        List<RoleAuthority> headerRoleAuthorityList = authorityList.stream().map(authority -> RoleAuthority.builder()
                .role(headerRole)
                .authority(authority)
                .build()).toList();
        roleAuthorityRepository.saveAll(headerRoleAuthorityList);
        List<RoleAuthority> leaderRoleAuthorityList = authorityList.stream()
                .filter(authority -> !AuthorityName.ORGANIZATION_INVITE.equals(authority.getName()))
                .map(authority -> RoleAuthority.builder()
                        .role(leaderRole)
                        .authority(authority)
                        .build()).toList();
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
        if (!header.equals(member)) {
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
        if (member.equals(team.getOrganization().getHeader()) || member.equals(team.getLeader())) {
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

        // 고유 팀은 팀원 추방 불가
        if (isMyTeam(team.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 조직장 추방 불가
        if (removeMember.equals(team.getOrganization().getHeader())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 팀장 추방 불가
        if (removeMember.equals(team.getLeader())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

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

        // 고유 팀은 팀 탈퇴 불가
        if (isMyTeam(team.getName())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 조직장은 떠나기가 없음, 팀해체만 가능
        if (member.equals(team.getOrganization().getHeader())) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        // 팀장은 떠나기가 없음, 팀해체만 가능
        if (member.equals(team.getLeader())) {
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
        if (!(member.equals(header) || member.equals(leader))) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        MemberTeam findMemberAndTeam = memberTeamRepository.findByMemberAndTeam(newLeader, team);
        // 팀장 위임은 현재 우리 팀원에게만 가능
        if (findMemberAndTeam == null) {
            throw new BusinessException(ExceptionCode.TEAM_MEMBER_NOT_FOUND);
        }

        // 팀장 위임은 조직장에게 불가능
        if (newLeader.equals(header)) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        Role leaderRole = roleRepository.findByTeamAndName(team, "LEADER");
        MemberRole findNewLeaderAndRole = memberRoleRepository.findByMemberAndTeam(newLeader, team);
        findNewLeaderAndRole.setRole(leaderRole);

        memberRoleRepository.save(findNewLeaderAndRole);
        if (!member.equals(header)) {
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
        if (!(member.equals(team.getOrganization().getHeader()) || member.equals(team.getLeader()))) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

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
    public void rotateDataKey() {
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
                startId = endId;
                endId += 1000L;
            } else {
                break;
            }
        }

    }

    private boolean isMyTeam(String teamName) {
        return "MY TEAM".equals(teamName);
    }

}
