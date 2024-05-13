package world.pasds.back.invitaion.entity;

import jakarta.persistence.*;
import lombok.*;
import world.pasds.back.common.BaseEntity;
import world.pasds.back.member.entity.Member;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.organization.entity.OrganizationRole;
import world.pasds.back.role.entity.Role;
import world.pasds.back.team.entity.Team;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invitation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member invitedBy;

    private String invitedMemberEmail;

    private LocalDateTime expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Enumerated(EnumType.STRING)
    private OrganizationRole organizationRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public String toString() {
        return "Invitation{" +
                "id=" + id +
                ", invitedBy=" + invitedBy +
                ", invitedMemberEmail='" + invitedMemberEmail + '\'' +
                ", expiredAt=" + expiredAt +
                ", organization=" + organization +
                ", organizationRole=" + organizationRole +
                ", team=" + team +
                ", role=" + role +
                '}';
    }
}
