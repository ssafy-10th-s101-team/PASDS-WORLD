package world.pasds.back.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import world.pasds.back.common.BaseEntity;
import world.pasds.back.team.entity.Team;

@Entity
@Getter
public class MemberTeam extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
