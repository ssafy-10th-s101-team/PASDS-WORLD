package world.pasds.back.member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.entity.MemberRole;
import world.pasds.back.role.entity.Role;
import world.pasds.back.team.entity.Team;

import java.util.List;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

    MemberRole findByTeamAndRole(Team team, Role role);

    MemberRole findByMemberAndTeam(Member member, Team team);

    Page<MemberRole> findAllByTeam(Team team, Pageable pageable);

    List<MemberRole> findAllByRole(Role role);

    boolean existsByRole(Role role);
}
