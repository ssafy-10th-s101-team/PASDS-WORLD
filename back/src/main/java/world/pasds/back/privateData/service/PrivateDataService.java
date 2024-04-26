package world.pasds.back.privateData.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.entity.MemberRole;
import world.pasds.back.member.entity.MemberTeam;
import world.pasds.back.member.repository.MemberRepository;
import world.pasds.back.member.repository.MemberRoleRepository;
import world.pasds.back.member.repository.MemberTeamRepository;
import world.pasds.back.privateData.entity.DataType;
import world.pasds.back.privateData.entity.dto.request.CreatePrivateDataRequestDto;
import world.pasds.back.privateData.entity.dto.request.UpdatePrivateDataRequestDto;
import world.pasds.back.role.entity.Role;
import world.pasds.back.role.entity.RoleAuthority;
import world.pasds.back.role.repository.RoleAuthorityRepository;
import world.pasds.back.role.repository.RoleRepository;
import world.pasds.back.privateData.entity.PrivateData;
import world.pasds.back.privateData.entity.PrivateDataRole;
import world.pasds.back.team.entity.Team;
import world.pasds.back.privateData.entity.dto.request.GetPrivateDataListRequestDto;
import world.pasds.back.privateData.entity.dto.request.GetPrivateDataRequestDto;
import world.pasds.back.privateData.entity.dto.response.GetPrivateDataListResponseDto;
import world.pasds.back.privateData.entity.dto.response.GetPrivateDataResponseDto;
import world.pasds.back.team.repository.PrivateDataRepository;
import world.pasds.back.team.repository.PrivateDataRoleRepository;
import world.pasds.back.team.repository.TeamRepository;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateDataService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final MemberTeamRepository memberTeamRepository;
    private final PrivateDataRepository privateDataRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final PrivateDataRoleRepository privateDataRoleRepository;
    private final RoleRepository roleRepository;
    private final RoleAuthorityRepository roleAuthorityRepository;

    @Transactional
    public List<GetPrivateDataListResponseDto> getPrivateDataList(GetPrivateDataListRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        // 팀 멤버인지 확인
        MemberTeam findMemberAndTeam = memberTeamRepository.findByMemberAndTeam(member, team);
        if (findMemberAndTeam == null) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        // 우리 팀 비밀 목록 조회
        List<PrivateData> privateData = privateDataRepository.findAllByTeam(team);

        // 팀내에서 역할을 부여받은 사용자인지 확인
        MemberRole findMemberRole = memberRoleRepository.findByMemberAndTeam(member, team);
        if (findMemberRole == null) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        Role role = findMemberRole.getRole();

        // 내가 조회 가능한 비밀 목록 추가
        List<PrivateData> canReadData = new ArrayList<>();
        for (PrivateData data : privateData) {
            if (privateDataRoleRepository.existsByPrivateDataAndRole(data, role)) { // 비밀에 나의 역할이 읽을 수 있는지 확인
                if (roleAuthorityRepository.checkAuthority(role, 2L)) {
                    canReadData.add(data);
                }
            }
        }

        return canReadData.stream().map(pd -> new GetPrivateDataListResponseDto(team.getId(), pd.getId(), pd.getTitle(), pd.getType(), pd.getCreatedBy(), pd.getPrivateDataId(), pd.getUrl())).collect(Collectors.toList());
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
    public void createPrivateData(CreatePrivateDataRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        // 비밀 생성 가능한지 권한 확인
        MemberRole findMemberRole = memberRoleRepository.findByMemberAndTeam(member, team);
        Role role = findMemberRole.getRole();

        if (!roleAuthorityRepository.checkAuthority(role, 1L)) {
            throw new BusinessException(ExceptionCode.PRIVATE_DATA_UNAUTHORIZED);
        }

        /**
         * KMS에게 암호화 키 달라고 한 뒤
         * 비밀 암호화하여 저장
         */
        byte[] encryptedDataKey = team.getEncryptedDataKey();
        byte[] encryptedIv = team.getEncryptedIv();
        byte[] encryptedPrivateData = requestDto.getContent().getBytes(StandardCharsets.UTF_8);

        PrivateData privateData = null;

        if (requestDto.getType().equals(DataType.PW)) {
            privateData = PrivateData.builder()
                    .team(team)
                    .type(requestDto.getType())
                    .title(requestDto.getTitle())
                    .content(encryptedPrivateData)
                    .memo(requestDto.getMemo())
                    .privateDataId(requestDto.getPrivateDataId())
                    .url(requestDto.getUrl())
                    .build();
        } else {
            privateData = PrivateData.builder()
                    .team(team)
                    .type(requestDto.getType())
                    .title(requestDto.getTitle())
                    .content(encryptedPrivateData)
                    .memo(requestDto.getMemo())
                    .build();
        }
        privateDataRepository.save(privateData);

        // 설정하고자 하는 역할 조회
        Role setRole = roleRepository.findById(requestDto.getRoleId()).orElseThrow(() -> new BusinessException(ExceptionCode.ROLE_NOT_FOUND));

        // 비밀_역할 저장
        PrivateDataRole pdr = PrivateDataRole.builder()
                .privateData(privateData)
                .role(setRole)
                .build();
        privateDataRoleRepository.save(pdr);
    }

    @Transactional
    public void updatePrivateDataRequestDto(UpdatePrivateDataRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        // 비밀 수정 가능한지 권한 확인
        MemberRole findMemberRole = memberRoleRepository.findByMemberAndTeam(member, team);
        Role role = findMemberRole.getRole();

        if (!roleAuthorityRepository.checkAuthority(role, 3L)) {
            throw new BusinessException(ExceptionCode.PRIVATE_DATA_UNAUTHORIZED);
        }

        PrivateData findPrivateData = privateDataRepository.findById(requestDto.getId()).orElseThrow(() -> new BusinessException(ExceptionCode.PRIVATE_DATA_NOT_FOUND));

        /**
         * KMS에게 암호화 키 달라고 한 뒤
         * 비밀 암호화하여 저장
         */
        byte[] encryptedDataKey = team.getEncryptedDataKey();
        byte[] encryptedIv = team.getEncryptedIv();
        byte[] encryptedPrivateData = requestDto.getContent().getBytes(StandardCharsets.UTF_8);

        PrivateData newPrivateData = null;

        if (findPrivateData.getType().equals(DataType.PW)) {
            newPrivateData = PrivateData.builder()
                    .team(team)
                    .type(findPrivateData.getType())
                    .title(requestDto.getTitle())
                    .content(encryptedPrivateData)
                    .memo(requestDto.getMemo())
                    .privateDataId(requestDto.getPrivateDataId())
                    .url(requestDto.getUrl())
                    .build();
        } else {
            newPrivateData = PrivateData.builder()
                    .team(team)
                    .type(findPrivateData.getType())
                    .title(requestDto.getTitle())
                    .content(encryptedPrivateData)
                    .memo(requestDto.getMemo())
                    .build();
        }

        // 설정하고자 하는 역할 조회
        Role setRole = roleRepository.findById(requestDto.getRoleId()).orElseThrow(() -> new BusinessException(ExceptionCode.ROLE_NOT_FOUND));

        // 비밀_역할 저장
        PrivateDataRole pdr = PrivateDataRole.builder()
                .privateData(newPrivateData)
                .role(setRole)
                .build();
        privateDataRoleRepository.save(pdr);
    }
}
