package world.pasds.back.team.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer roleCount;

    private Integer secretCount;

    @Column(length = 32, columnDefinition = "BINARY(32)")
    private byte[] encryptedDataKey;

    @Column(length = 32, columnDefinition = "BINARY(32)")
    private byte[] encryptedIv;

    private LocalDateTime expiredAt;

}
