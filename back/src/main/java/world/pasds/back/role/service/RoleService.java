package world.pasds.back.role.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.authority.entity.Authority;
import world.pasds.back.authority.entity.AuthorityName;
import world.pasds.back.authority.repository.AuthorityRepository;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.entity.MemberRole;
import world.pasds.back.member.entity.MemberTeam;
import world.pasds.back.member.repository.MemberRepository;
import world.pasds.back.member.repository.MemberRoleRepository;
import world.pasds.back.member.repository.MemberTeamRepository;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.role.entity.Role;
import world.pasds.back.role.entity.RoleAuthority;
import world.pasds.back.role.entity.dto.request.CreateRoleRequestDto;
import world.pasds.back.role.entity.dto.request.DeleteRoleRequestDto;
import world.pasds.back.role.entity.dto.request.UpdateRoleRequestDto;
import world.pasds.back.role.entity.dto.response.GetRoleResponseDto;
import world.pasds.back.role.repository.RoleAuthorityRepository;
import world.pasds.back.role.repository.RoleRepository;
import world.pasds.back.team.entity.Team;
import world.pasds.back.team.entity.dto.request.AssignRoleRequestDto;
import world.pasds.back.team.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final TeamRepository teamRepository;
    private final RoleRepository roleRepository;
    private final RoleAuthorityRepository roleAuthorityRepository;
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final MemberTeamRepository memberTeamRepository;
    private final AuthorityRepository authorityRepository;

    @Transactional
    public List<GetRoleResponseDto> getRole(Long teamId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));
        MemberRole memberRole = memberRoleRepository.findByMemberAndTeam(member, team);
        Role role = memberRole.getRole();
        List<String> roleAuthorityList = roleAuthorityRepository.findAllByRole(role)
                .stream()
                .map(roleAuthority -> String.valueOf(roleAuthority.getAuthority().getName()))
                .toList();
        for (String s : roleAuthorityList) {
            System.out.println(s);
        }

        // 권한 확인
        if (roleAuthorityList.contains(String.valueOf(AuthorityName.ROLE_READ))) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        List<Role> roleList = roleRepository.findAllByTeam(team);
        return roleList.stream().map(r -> GetRoleResponseDto.builder().roleId(r.getId()).name(r.getName()).build()).collect(Collectors.toList());
    }

    @Transactional
    public void createRole(CreateRoleRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));
        MemberRole memberRole = memberRoleRepository.findByMemberAndTeam(member, team);
        Role role = memberRole.getRole();
        List<String> roleAuthorityList = roleAuthorityRepository.findAllByRole(role)
                .stream()
                .map(roleAuthority -> String.valueOf(roleAuthority.getAuthority().getName()))
                .toList();

        // 권한 확인
        if (roleAuthorityList.contains(String.valueOf(AuthorityName.ROLE_CREATE))) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        Role createRole = Role.builder()
                .name(requestDto.getRoleName())
                .team(team)
                .build();

        if ("HEADER".equals(createRole.getName()) ||
                "LEADER".equals(createRole.getName()) ||
                "MEMBER".equals(createRole.getName())) {
            throw new BusinessException(ExceptionCode.AUTHORITY_NAME_CONFLICT);
        }
        Role savedRole = roleRepository.save(createRole);

        List<RoleAuthority> saveRoleAuthorityList = new ArrayList<>();
        for (Long authorityId : requestDto.getAuthorities()) {
            Authority authority = authorityRepository.findById(authorityId).orElseThrow(() -> new BusinessException(ExceptionCode.AUTHORITY_NOT_FOUND));
            if (AuthorityName.TEAM_DELETE == authority.getName()) {    // 팀 삭제 권한이 있는 역할 생성 불가
                throw new BusinessException(ExceptionCode.BAD_REQUEST);
            }
            RoleAuthority roleAuthority = RoleAuthority.builder()
                    .role(savedRole)
                    .authority(authority)
                    .build();
            saveRoleAuthorityList.add(roleAuthority);
        }

        roleAuthorityRepository.saveAll(saveRoleAuthorityList);
    }

    @Transactional
    public void updateRole(UpdateRoleRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));
        MemberRole memberRole = memberRoleRepository.findByMemberAndTeam(member, team);
        Role role = memberRole.getRole();
        List<String> roleAuthorityList = roleAuthorityRepository.findAllByRole(role)
                .stream()
                .map(roleAuthority -> String.valueOf(roleAuthority.getAuthority().getName()))
                .toList();

        // 권한 확인
        if (roleAuthorityList.contains(String.valueOf(AuthorityName.ROLE_UPDATE))) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        // 이미 존재하는 역할명으로 수정 불가
        if (roleRepository.existsByTeamAndName(team, requestDto.getNewRoleName())) {
            throw new BusinessException(ExceptionCode.ROLE_EXISTS);
        }

        // 역할명 변경
        Role newRole = roleRepository.findById(requestDto.getRoleId()).orElseThrow(() -> new BusinessException(ExceptionCode.ROLE_NOT_FOUND));
        newRole.setName(requestDto.getNewRoleName());
        roleRepository.save(newRole);

        // 역할의 권한 수정
        List<RoleAuthority> newAuthorities = requestDto.getAuthorities()
                .stream()
                .filter(authority -> AuthorityName.TEAM_DELETE != authority.getName())
                .map(authority -> RoleAuthority
                        .builder()
                        .role(newRole)
                        .authority(authority)
                        .build()).toList();
        List<RoleAuthority> authorityList = roleAuthorityRepository.findAllByRole(role);
        roleAuthorityRepository.deleteAll(authorityList);
        roleAuthorityRepository.saveAll(newAuthorities);
    }

    @Transactional
    public void deleteRole(DeleteRoleRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));
        MemberRole memberRole = memberRoleRepository.findByMemberAndTeam(member, team);
        Role role = memberRole.getRole();
        List<String> roleAuthorityList = roleAuthorityRepository.findAllByRole(role)
                .stream()
                .map(roleAuthority -> String.valueOf(roleAuthority.getAuthority().getName()))
                .toList();

        // 권한 확인
        if (roleAuthorityList.contains(String.valueOf(AuthorityName.ROLE_DELETE))) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        Role deleteRole = roleRepository.findById(requestDto.getRoleId()).orElseThrow(() -> new BusinessException(ExceptionCode.ROLE_NOT_FOUND));

        if (memberRoleRepository.existsByRoleAndTeam(deleteRole, team)) {
            throw new BusinessException(ExceptionCode.ROLE_EXISTS);
        }

        roleRepository.delete(role);
    }

    @Transactional
    public void assignRole(AssignRoleRequestDto requestDto, Long memberId) {
        Member assignedMember = memberRepository.findById(requestDto.getAssignedMemberId()).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));
        Role role = roleRepository.findById(requestDto.getRoleId()).orElseThrow(() -> new BusinessException(ExceptionCode.ROLE_NOT_FOUND));
        Organization organization = team.getOrganization();

        if (!(team.getLeader().getId().equals(member.getId()) || organization.getHeader().getId().equals(member.getId()))) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        // 팀에 속한 멤버인지 확인
        MemberTeam findMemberAndTeam = memberTeamRepository.findByMemberAndTeam(assignedMember, team);
        if (findMemberAndTeam == null) {
            throw new BusinessException(ExceptionCode.TEAM_MEMBER_NOT_FOUND);
        }

        // 이미 역할을 부여받은 멤버인지 확인
        MemberRole findMemberAndRole = memberRoleRepository.findByMemberAndRole(assignedMember, role);
        if (findMemberAndRole == null) {
            MemberRole memberRole = MemberRole.builder()
                    .member(assignedMember)
                    .role(role)
                    .build();
            memberRoleRepository.save(memberRole);
        } else {
            findMemberAndRole.setRole(role);
            memberRoleRepository.save(findMemberAndRole);
        }
    }
}
