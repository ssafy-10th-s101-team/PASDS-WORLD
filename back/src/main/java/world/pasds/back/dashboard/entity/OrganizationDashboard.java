package world.pasds.back.dashboard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import world.pasds.back.organization.entity.Organization;

@Entity
@Getter
public class OrganizationDashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(columnDefinition = "SMALLINT")
    private int year;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Setter
    @Column(columnDefinition = "TINYINT")
    private int month;

    private int count;

    private int views;

    private int rotate;

}
