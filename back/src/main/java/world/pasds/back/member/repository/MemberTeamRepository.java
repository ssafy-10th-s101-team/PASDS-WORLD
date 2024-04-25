package world.pasds.back.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.entity.MemberTeam;
import world.pasds.back.team.entity.Team;

@Repository
public interface MemberTeamRepository extends JpaRepository<MemberTeam, Long> {

    MemberTeam findByMemberAndTeam(Member member, Team team);

    MemberTeam findAllByMember(Member member);
}
