package world.pasds.back.member.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RoleAuthority {
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
