package world.pasds.back.organization.entity;

import jakarta.persistence.*;
import lombok.Getter;
import world.pasds.back.member.entity.Member;

@Entity
@Getter
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;

    private Integer teamCount;

}
