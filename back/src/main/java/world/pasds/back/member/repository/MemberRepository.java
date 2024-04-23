package world.pasds.back.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
