package world.pasds.back.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import world.pasds.back.common.BaseEntity;
import world.pasds.back.team.entity.Role;

@Entity
@Getter
public class MemberRole extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
