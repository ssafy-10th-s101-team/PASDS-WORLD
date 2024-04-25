package world.pasds.back.role.entity;

import jakarta.persistence.*;
import lombok.*;
import world.pasds.back.common.BaseEntity;
import world.pasds.back.team.entity.Team;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private String name;
}
