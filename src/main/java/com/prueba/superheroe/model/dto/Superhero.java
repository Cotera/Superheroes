package com.prueba.superheroe.model.dto;

import lombok.Data;

@Data
public class Superhero {

    private Long id;

    private String name;
    private String ability;
    private String universe;


    public Superhero withId(Long id) {
        this.id = id;
        return this;
    }

    public Superhero withName(String name) {
        this.name = name;
        return this;
    }

    public Superhero withAbility(String ability) {
        this.ability = ability;
        return this;
    }

    public Superhero withUniverse(String universe) {
        this.universe = universe;
        return this;
    }

}
