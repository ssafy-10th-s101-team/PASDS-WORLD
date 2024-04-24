package world.pasds.kms.masterkey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import world.pasds.kms.masterkey.entity.MasterKey;

public interface MasterKeyRepository extends JpaRepository<MasterKey, Long> {
}
