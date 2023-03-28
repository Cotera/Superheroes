package com.prueba.superheroe.web;

import com.prueba.superheroe.model.dto.Superhero;
import com.prueba.superheroe.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/superheros")
public class SuperheroController {

    @Autowired
    private SuperheroService superHeroService;

    @GetMapping("/{id}")
    public Superhero getOne(@PathVariable Long id) {

        return null;
    }

    @GetMapping
    public List<Superhero> getAll() {

        return null;
    }

    @GetMapping(value = "/search", params = {"name"})
    public List<Superhero> filterByName(@RequestParam("name") String search) {

        return null;
    }

    @PutMapping("/{id}")
    public Superhero update(@PathVariable Long id, @RequestBody Superhero superheroe) {

        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

    }
}
