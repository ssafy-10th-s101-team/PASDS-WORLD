package world.pasds.back.member.entity;

import jakarta.persistence.*;
import lombok.*;
import world.pasds.back.common.BaseEntity;
import world.pasds.back.role.entity.Role;
import world.pasds.back.team.entity.Team;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRole extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
