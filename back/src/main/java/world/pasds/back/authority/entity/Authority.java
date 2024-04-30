package world.pasds.back.authority.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthorityName name;
}
