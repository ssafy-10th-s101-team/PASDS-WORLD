package world.pasds.back.role.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.authority.entity.Authority;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.entity.MemberRole;
import world.pasds.back.member.entity.MemberTeam;
import world.pasds.back.member.repository.MemberRepository;
import world.pasds.back.member.repository.MemberRoleRepository;
import world.pasds.back.member.repository.MemberTeamRepository;
import world.pasds.back.role.entity.Role;
import world.pasds.back.role.entity.RoleAuthority;
import world.pasds.back.role.entity.dto.request.CreateRoleRequestDto;
import world.pasds.back.role.entity.dto.request.DeleteRoleRequestDto;
import world.pasds.back.role.entity.dto.request.UpdateRoleRequestDto;
import world.pasds.back.role.entity.dto.response.getRoleResponseDto;
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

    @Transactional
    public List<getRoleResponseDto> getRole(Long teamId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        if (!team.getLeader().equals(member)) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        List<Role> roleList = roleRepository.findAllByTeam(team);
        return roleList.stream().map(role -> new getRoleResponseDto(role.getId(), role.getName())).collect(Collectors.toList());
    }

    @Transactional
    public void createRole(CreateRoleRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        if (!team.getLeader().equals(member)) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        Role role = Role.builder()
                .name(requestDto.getRoleName())
                .team(team)
                .build();
        Role savedRole = roleRepository.save(role);

        List<RoleAuthority> roleAuthorityList = new ArrayList<>();
        for (Authority authority : requestDto.getAuthorities()) {
            RoleAuthority roleAuthority = RoleAuthority.builder()
                    .role(savedRole)
                    .authority(authority)
                    .build();
            roleAuthorityList.add(roleAuthority);
        }

        roleAuthorityRepository.saveAll(roleAuthorityList);
    }

    @Transactional
    public void updateRole(UpdateRoleRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        if (!team.getLeader().equals(member)) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        Role role = roleRepository.findById(requestDto.getRoleId()).orElseThrow(() -> new BusinessException(ExceptionCode.ROLE_NOT_FOUND));
        if (roleRepository.existsByTeamAndName(team, requestDto.getNewRoleName())) {
            throw new BusinessException(ExceptionCode.ROLE_EXISTS);
        }
        role.setName(requestDto.getNewRoleName());
        roleRepository.save(role);

        List<RoleAuthority> newAuthorities = requestDto.getAuthorities()
                .stream()
                .map(authority -> RoleAuthority.
                        builder()
                        .authority(authority)
                        .role(role)
                        .build()).toList();
        List<RoleAuthority> authorityList = roleAuthorityRepository.findAllByRole(role);
        roleAuthorityRepository.deleteAll(authorityList);
        roleAuthorityRepository.saveAll(newAuthorities);
    }

    @Transactional
    public void deleteRole(DeleteRoleRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        if (!team.getLeader().equals(member)) {
            throw new BusinessException(ExceptionCode.TEAM_UNAUTHORIZED);
        }

        Role role = roleRepository.findById(requestDto.getRoleId()).orElseThrow(() -> new BusinessException(ExceptionCode.ROLE_NOT_FOUND));

        roleRepository.delete(role);
    }

    @Transactional
    public void assignRole(AssignRoleRequestDto requestDto, Long memberId) {
        Member assignedMember = memberRepository.findById(requestDto.getAssignedMemberId()).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));
        Role role = roleRepository.findById(requestDto.getRoleId()).orElseThrow(() -> new BusinessException(ExceptionCode.ROLE_NOT_FOUND));

        // 팀장인지 확인
        if (!team.getLeader().equals(member)) {
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
