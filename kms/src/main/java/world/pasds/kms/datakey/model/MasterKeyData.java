package world.pasds.kms.datakey.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterKeyData{
    private byte[] value;
    private byte[] iv;

    public MasterKeyData(byte[] value, byte[] iv){
        this.value = value;
        this.iv = iv;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        return sb.append(value).append(iv).toString();
    }
}