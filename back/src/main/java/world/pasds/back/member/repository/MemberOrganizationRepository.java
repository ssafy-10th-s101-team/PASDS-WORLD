package world.pasds.back.member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.entity.MemberOrganization;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.organization.entity.OrganizationRole;

import java.util.List;

@Repository
public interface MemberOrganizationRepository extends JpaRepository<MemberOrganization, Long> {

    List<MemberOrganization> findAllByMember(Member member);
    List<MemberOrganization> findAllByMemberAndOrganizationRoleIn(Member member, List<OrganizationRole> roles);

    MemberOrganization findByMemberAndOrganization(Member member, Organization organization);

    Page<MemberOrganization> findAllByOrganization(Organization organization, Pageable pageable);

    boolean existsByMemberAndOrganization(Member member, Organization organization);

    boolean existsByMemberAndOrganizationAndOrganizationRoleIn(Member member,Organization organization, List<OrganizationRole> organizationRoles);
}
