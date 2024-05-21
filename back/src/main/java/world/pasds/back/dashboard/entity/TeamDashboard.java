package world.pasds.back.dashboard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import world.pasds.back.team.entity.Team;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TeamDashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(columnDefinition = "SMALLINT")
    private int year;

    @Column(columnDefinition = "TINYINT")
    private int month;

    private int count;

    private int views;

    private int rotate;

    public TeamDashboard(int year, int month, Team team) {
        this.year = year;
        this.month = month;
        this.team = team;
    }


}
