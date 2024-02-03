package GDSC.realWorld.entity;

import jakarta.persistence.*;

@Entity
public class Tag {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
