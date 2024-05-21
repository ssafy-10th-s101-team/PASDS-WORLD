package world.pasds.kms.masterkey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import world.pasds.kms.masterkey.entity.MasterKey;

import java.util.List;

public interface MasterKeyRepository extends JpaRepository<MasterKey, Long> {
    List<MasterKey> findTop2ByOrderByIdDesc();

    MasterKey findMasterKeyById(Long id);
}
