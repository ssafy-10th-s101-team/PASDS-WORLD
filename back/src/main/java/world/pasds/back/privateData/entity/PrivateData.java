package world.pasds.back.privateData.entity;

import jakarta.persistence.*;
import lombok.*;
import world.pasds.back.common.BaseEntity;
import world.pasds.back.team.entity.Team;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateData extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(EnumType.STRING)
    private DataType type;

    @Setter
    private String title;

    @Lob
    @Setter
    private byte[] content;

    @Setter
    private String memo;

    @Setter
    private String privateDataId;

    @Setter
    private String url;

    private Integer count;
}
