package world.pasds.back.totp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import world.pasds.back.member.entity.Member;

@Repository
public interface TotpRepository extends JpaRepository<Member,Long> {
	@Query("select m.encryptedTotpDataKey from Member m where m.id = :id")
	Optional<byte[]> findEncryptedTotpDataKeyByMemberId(@Param("id") Long id);

	@Query("select m.encryptedTotpKey from Member m where m.id = :id")
	Optional<byte[]> findEncryptedTotpKeyByMemberId(@Param("id") Long id);

	@Query("select m.encryptedTotpIv from Member m where m.id = :id")
    Optional<byte[]> findEncryptedTotpIvByMemberId(@Param("id")Long memberId);

	@Query("select m.masterKeyVersion from Member m where m.id = :id")
	Long findMasterKeyVersionByMemberId(@Param("id")Long memberId);
}
