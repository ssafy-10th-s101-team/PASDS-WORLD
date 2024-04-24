package world.pasds.back.invitaion.entity;

import jakarta.persistence.*;
import world.pasds.back.common.BaseEntity;
import world.pasds.back.member.entity.Member;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.team.entity.Team;

import java.time.LocalDateTime;

@Entity
public class Invitation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member invitedBy;

    private String invitedMemberEmail;

    private LocalDateTime expiredAt;

    @OneToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

}
