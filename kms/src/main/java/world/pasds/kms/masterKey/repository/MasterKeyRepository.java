package world.pasds.kms.masterKey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import world.pasds.kms.masterKey.entity.MasterKey;

public interface MasterKeyRepository extends JpaRepository<MasterKey, Long> {
}
