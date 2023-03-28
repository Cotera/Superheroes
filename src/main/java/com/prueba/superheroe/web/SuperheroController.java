package com.prueba.superheroe.web;

import com.prueba.superheroe.model.dto.Superhero;
import com.prueba.superheroe.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SuperheroController {

    @Autowired
    private SuperheroService superHeroService;

    public Superhero getOne() {

        return null;
    }

    public List<Superhero> getAll() {

        return null;
    }

    public List<Superhero> filterByName() {

        return null;
    }

    public Superhero update() {

        return null;
    }

    public void delete() {

    }
}
