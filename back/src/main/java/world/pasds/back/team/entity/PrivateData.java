package world.pasds.back.team.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class PrivateData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(EnumType.STRING)
    private DataType type;

    private String title;

    @Lob
    private byte[] content;

    private String memo;
}
