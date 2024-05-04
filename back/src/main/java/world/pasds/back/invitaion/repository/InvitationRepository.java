package world.pasds.back.invitaion.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.invitaion.entity.Invitation;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.team.entity.Team;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    List<Invitation> findAllOrganizationInvitationByInvitedMemberEmailAndOrganizationOrderByCreatedAtDesc(String invitedMemberEmail, Organization organization);
    List<Invitation> findAllTeamInvitationByInvitedMemberEmailAndOrganizationAndTeamOrderByCreatedAtDesc(String invitedMemberEmail, Organization organization, Team team);

    List<Invitation> findAllByInvitedMemberEmail(String email);
    List<Invitation> findAllByInvitedMemberEmail (String email, Pageable pageable);
}
