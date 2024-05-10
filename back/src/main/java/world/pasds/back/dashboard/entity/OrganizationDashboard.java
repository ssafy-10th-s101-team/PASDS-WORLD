package world.pasds.back.dashboard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.team.entity.Team;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrganizationDashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "SMALLINT")
    private int year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Column(columnDefinition = "TINYINT")
    private int month;

    private int count;

    private int views;

    private int rotate;

    public OrganizationDashboard(int year, int month, Organization organization) {
        this.year = year;
        this.month = month;
        this.organization = organization;
    }

}
