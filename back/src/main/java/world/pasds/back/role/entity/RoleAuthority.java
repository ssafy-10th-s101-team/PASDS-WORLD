package world.pasds.back.role.entity;

import jakarta.persistence.*;
import lombok.Getter;
import world.pasds.back.authority.entity.Authority;
import world.pasds.back.common.BaseEntity;

@Entity
@Getter
public class RoleAuthority extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;
}
