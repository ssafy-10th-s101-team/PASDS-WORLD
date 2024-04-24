package world.pasds.back.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import world.pasds.back.common.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    @Column(length = 32, columnDefinition = "BINARY(32)")
    private byte[] encryptedTotpKey;

    @Column(length = 32, columnDefinition = "BINARY(32)")
    private byte[] encryptedTotpDataKey;

    @Column(length = 32, columnDefinition = "BINARY(32)")
    private byte[] encryptedTotpIvKey;

    private LocalDateTime expiredAt;
}
