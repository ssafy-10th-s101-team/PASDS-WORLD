package world.pasds.back.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.entity.MemberOrganization;

import java.util.List;

@Repository
public interface MemberOrganizationRepository extends JpaRepository<MemberOrganization, Long> {

    List<MemberOrganization> findAllByMember(Member member);
}
