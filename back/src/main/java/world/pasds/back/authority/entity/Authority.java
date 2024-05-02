package world.pasds.back.authority.entity;

import jakarta.persistence.*;
import lombok.Getter;
import world.pasds.back.common.BaseEntity;

@Entity
@Getter
public class Authority extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthorityName name;
}
