package world.pasds.back.privateData.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import world.pasds.back.privateData.entity.PrivateData;
import world.pasds.back.role.entity.Role;

public interface PrivateDataCustomRepository {
    Page<PrivateData> findAccessiblePrivateData(Long teamId, Role userRole, Pageable pageable);
}
