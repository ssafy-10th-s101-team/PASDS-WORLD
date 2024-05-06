package world.pasds.back.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import world.pasds.back.common.BaseEntity;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    @Setter
    @Column(length = 48, columnDefinition = "BINARY(48)")
    private byte[] encryptedTotpKey;

    @Setter
    @Column(length = 48, columnDefinition = "BINARY(48)")
    private byte[] encryptedTotpDataKey;

    @Setter
    @Column(length = 32, columnDefinition = "BINARY(32)")
    private byte[] encryptedTotpIv;

    @Setter
    private LocalDateTime expiredAt;
}
