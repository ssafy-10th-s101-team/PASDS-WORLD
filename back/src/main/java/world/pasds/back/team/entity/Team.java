package world.pasds.back.team.entity;

import jakarta.persistence.*;
import lombok.*;
import world.pasds.back.common.BaseEntity;
import world.pasds.back.member.entity.Member;
import world.pasds.back.organization.entity.Organization;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member header;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    private String name;

    private Integer roleCount;

    private Integer secretCount;

    @Column(length = 32, columnDefinition = "BINARY(32)")
    private byte[] encryptedDataKey;

    @Column(length = 32, columnDefinition = "BINARY(32)")
    private byte[] encryptedIv;

    private LocalDateTime expiredAt;

}
