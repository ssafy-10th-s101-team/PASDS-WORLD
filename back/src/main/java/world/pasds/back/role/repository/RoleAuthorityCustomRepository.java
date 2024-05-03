package world.pasds.back.role.repository;

import world.pasds.back.authority.entity.AuthorityName;
import world.pasds.back.role.entity.Role;

public interface RoleAuthorityCustomRepository {
    boolean checkAuthority(Role role, AuthorityName authority);
}
