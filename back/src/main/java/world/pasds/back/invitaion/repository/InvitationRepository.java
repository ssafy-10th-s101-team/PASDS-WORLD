package world.pasds.back.invitaion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.invitaion.entity.Invitation;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
