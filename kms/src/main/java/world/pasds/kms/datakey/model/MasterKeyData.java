package world.pasds.kms.datakey.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MasterKeyData{
    private Long id;
    private byte[] value;
    private byte[] iv;
    private long expirySeconds;

    public MasterKeyData(Long id, byte[] value, byte[] iv, long expirySeconds){
        this.id = id;
        this.value = value;
        this.iv = iv;
        this.expirySeconds = expirySeconds;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        return sb.append(value).append(iv).toString();
    }
}