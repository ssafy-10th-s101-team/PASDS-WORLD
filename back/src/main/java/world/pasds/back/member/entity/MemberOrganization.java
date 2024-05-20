package world.pasds.back.member.entity;

import jakarta.persistence.*;
import lombok.*;
import world.pasds.back.common.BaseEntity;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.organization.entity.OrganizationRole;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrganization extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Setter
    @Enumerated(EnumType.STRING)
    private OrganizationRole organizationRole;
}
