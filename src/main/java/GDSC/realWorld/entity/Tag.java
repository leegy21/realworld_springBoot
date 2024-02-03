package GDSC.realWorld.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Tag {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
}
