//package world.pasds.kms.masterKey.entity;
//
//import jakarta.persistence.*;
//
//@Entity
//public class MasterKey {
//    @Id
//    private Long id;
//
//    // prev/cur
//    @Enumerated(EnumType.STRING) // ENUM 타입 매핑
//    private Type type;
//
//
//    @Column(length = 32) //binary
//    private byte[] value;
//
//    @Column(length = 16) //binary
//    private byte[] iv;
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }
//}
//
//enum Type {
//    PREV, CUR
//}
