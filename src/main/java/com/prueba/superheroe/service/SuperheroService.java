package com.prueba.superheroe.service;

import com.prueba.superheroe.model.dto.Superhero;
import com.prueba.superheroe.repository.SuperheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperheroService {

    @Autowired
    private SuperheroRepository superheroRepository;

    public SuperheroService() {
        super();
    }

    public Superhero getOne(Long id) {

        return null;
    }

    public List<Superhero> getAll() {

        return null;
    }

    public List<Superhero> filterByName(String search) {

        return null;
    }

    public Superhero update(Long id, Superhero superhero) {

        return null;
    }

    public void delete(Long id) {

    }

}
