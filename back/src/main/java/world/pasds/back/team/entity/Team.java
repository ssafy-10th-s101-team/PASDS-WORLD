package world.pasds.back.team.entity;

import jakarta.persistence.*;
import lombok.*;
import world.pasds.back.common.BaseEntity;
import world.pasds.back.invitaion.entity.Invitation;
import world.pasds.back.member.entity.Member;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.role.entity.Role;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member leader;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Setter
    private String name;

    private Integer roleCount;

    private Integer secretCount;

    @Setter
    @Column(length = 48, columnDefinition = "BINARY(48)")
    private byte[] encryptedDataKey;

    @Setter
    @Column(length = 32, columnDefinition = "BINARY(32)")
    private byte[] encryptedIv;

    @Setter
    private LocalDateTime expiredAt;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> roles;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invitation> invitations;

}
