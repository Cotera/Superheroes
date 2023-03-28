package com.prueba.superheroe.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SUPERHERO")
public class SuperheroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ABILITY")
    private String ability;
    @Column(name = "UNIVERSE")
    private String universe;

    public SuperheroEntity() {

    }


}
