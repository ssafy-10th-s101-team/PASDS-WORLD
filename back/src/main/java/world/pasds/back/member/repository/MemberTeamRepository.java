package world.pasds.back.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.entity.MemberTeam;
import world.pasds.back.team.entity.Team;

import java.util.List;

@Repository
public interface MemberTeamRepository extends JpaRepository<MemberTeam, Long> {

    MemberTeam findByMemberAndTeam(Member member, Team team);

    boolean existsByMemberAndTeam(Member member, Team team);

    @Query("SELECT mt " +
            "FROM MemberTeam mt " +
            "WHERE mt.member.id = :memberId AND mt.team.id IN (SELECT t.id FROM Team t WHERE t.organization.id = :organizationId)")
    List<MemberTeam> findByMemberIdAndOrganizationId(@Param("memberId") Long memberId, @Param("organizationId") Long organizationId);
}
