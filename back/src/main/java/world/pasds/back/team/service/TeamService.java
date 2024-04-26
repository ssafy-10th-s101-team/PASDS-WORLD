package world.pasds.back.team.service;

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
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.organization.repository.OrganizationRepository;
import world.pasds.back.role.entity.Role;
import world.pasds.back.role.entity.RoleAuthority;
import world.pasds.back.role.repository.RoleAuthorityRepository;
import world.pasds.back.role.repository.RoleRepository;
import world.pasds.back.team.entity.PrivateData;
import world.pasds.back.team.entity.PrivateDataRole;
import world.pasds.back.team.entity.Team;
import world.pasds.back.team.entity.dto.request.*;
import world.pasds.back.team.entity.dto.response.GetPrivateDataListResponseDto;
import world.pasds.back.team.entity.dto.response.GetPrivateDataResponseDto;
import world.pasds.back.team.entity.dto.response.GetTeamsResponseDto;
import world.pasds.back.team.repository.PrivateDataRepository;
import world.pasds.back.team.repository.PrivateDataRoleRepository;
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
    private final PrivateDataRepository privateDataRepository;
    private final PrivateDataRoleRepository privateDataRoleRepository;
    private final InvitationService invitationService;
    private final RoleRepository roleRepository;
    private final RoleAuthorityRepository roleAuthorityRepository;

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
    public List<GetPrivateDataListResponseDto> getPrivateDataList(GetPrivateDataListRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        /**
         * Todo 권한확인
         */

        List<PrivateData> privateDataList = privateDataRepository.findAllByTeam(team);
        return privateDataList.stream().map(pd -> new GetPrivateDataListResponseDto(organization.getId(), team.getId(), team.getName())).collect(Collectors.toList());
    }

    @Transactional
    public GetPrivateDataResponseDto getPrivateData(GetPrivateDataRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        // 팀 멤버인지 확인
        MemberTeam findMemberAndTeam = memberTeamRepository.findByMemberAndTeam(member, team);
        if (findMemberAndTeam == null) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        PrivateData privateData = privateDataRepository.findById(requestDto.getPrivateDataId()).orElseThrow(() -> new BusinessException(ExceptionCode.PRIVATE_DATA_NOT_FOUND));

        MemberRole memberRole = null;
        // 비밀 역할 확인
        List<PrivateDataRole> findPrivateDataRole = privateDataRoleRepository.findAllByPrivateData(privateData);
        for (PrivateDataRole privateDataRole : findPrivateDataRole) {
            Role findRole = roleRepository.findById(privateDataRole.getRole().getId()).orElseThrow(() -> new BusinessException(ExceptionCode.ROLE_NOT_FOUND));
            MemberRole findMemberAndRole = memberRoleRepository.findByMemberAndRole(member, findRole);
            if (findMemberAndRole != null) {
                memberRole = findMemberAndRole;
                break;
            }
        }

        // 해당 멤버는 비밀에 대한 역할을 가지지 않음
        if (memberRole == null) {
            throw new BusinessException(ExceptionCode.PRIVATE_DATA_UNAUTHORIZED);
        }

        // 비밀을 읽을 권한이 있는 역할을 가지고 있는지 확인
        boolean canRead = false;
        List<RoleAuthority> roleAuthorityList = roleAuthorityRepository.findAllByRole(memberRole.getRole());
        for (RoleAuthority roleAuthority : roleAuthorityList) {
            if (roleAuthority.getAuthority().getId() == 2) {
                canRead = true;
                break;
            }
        }

        if (!canRead) {
            throw new BusinessException(ExceptionCode.PRIVATE_DATA_UNAUTHORIZED);
        }

        /**
         * KMS에게 복호화 키 달라고 한 뒤
         * 비밀 복호화하여 응답
         */
        byte[] encryptedDataKey = team.getEncryptedDataKey();
        byte[] encryptedIv = team.getEncryptedIv();
        byte[] encrpytedPrivateData = privateData.getContent();
        String decryptedData = null;

        return new GetPrivateDataResponseDto(decryptedData);
    }

    @Transactional
    public void createTeam(CreateTeamRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        if (teamRepository.existsByName(requestDto.getTeamName())) {
            throw new BusinessException(ExceptionCode.TEAM_NAME_CONFLICT);
        }

        /**
         * Todo 팀 비밀키 발급
         */
        byte[] encryptedDataKey = null;
        byte[] encryptedIv = null;
        LocalDateTime expiredAt = null;

        Team newTeam = Team.builder()
                .header(member)
                .organization(organization)
                .name(requestDto.getTeamName())
                .roleCount(0)
                .secretCount(0)
                .encryptedDataKey(encryptedDataKey)
                .encryptedIv(encryptedIv)
                .expiredAt(expiredAt)
                .build();

        teamRepository.save(newTeam);
    }

    @Transactional
    public void deleteTeam(DeleteTeamRequestDto requestDto, Long memberId) {
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));
        if (team.getHeader().getId() != memberId) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }
        teamRepository.delete(team);
    }

    @Transactional
    public void inviteMemberToTeam(InviteMemberToTeamRequestDto requestDto, Long memberId) {
        /**
         * Todo 팀 초대권한 확인
         */
        Member sender = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Member receiver = memberRepository.findByEmail(requestDto.getInviteMemberEmail());
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        invitationService.inviteMemberToTeam(organization, team, sender, requestDto.getInviteMemberEmail());

        // 우리 회원인 경우
        if (receiver != null) {
            MemberOrganization findMemberAndOrganization = memberOrganizationRepository.findByMemberAndOrganization(receiver, organization);
            if (findMemberAndOrganization != null) {    // 이미 우리 회원인 경우
                throw new BusinessException(ExceptionCode.BAD_REQUEST);
            } else {
                /**
                 * Todo 알림 보내기
                 */
            }
        }
    }

    @Transactional
    public void removeMemberFromTeam(RemoveMemberFromTeamRequestDto requestDto, Long memberId) {
        /**
         * Todo 팀원 추방권한 확인
         */
        Member findMember = memberRepository.findByEmail(requestDto.getRemoveMemberEmail());
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));
        if (findMember == null) {
            throw new BusinessException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        MemberTeam findMemberAndTeam = memberTeamRepository.findByMemberAndTeam(findMember, team);
        memberTeamRepository.delete(findMemberAndTeam);
    }
}
