package world.pasds.kms.masterkey.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import world.pasds.kms.common.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MasterKey extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 32) //binary
    private byte[] value;

    @Column(length = 16) //binary
    private byte[] iv;

    private LocalDateTime expiredAt;
}
