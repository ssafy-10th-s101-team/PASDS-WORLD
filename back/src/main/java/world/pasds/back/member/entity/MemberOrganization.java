package world.pasds.back.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import world.pasds.back.organization.entity.Organization;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrganization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
