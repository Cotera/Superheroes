package com.prueba.superheroe.service;

import com.prueba.superheroe.exception.NotFoundException;
import com.prueba.superheroe.model.SuperheroEntity;
import com.prueba.superheroe.model.dto.Superhero;
import com.prueba.superheroe.repository.SuperheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SuperheroService {

    @Autowired
    private SuperheroRepository superheroRepository;

    public SuperheroService() {
        super();
    }

    public Optional<Superhero> getOne(Long id) {
        SuperheroEntity superheroEntity = superheroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Superhero not found"));

        return Optional.of(new Superhero()
                .withId(superheroEntity.getId())
                .withName(superheroEntity.getName())
                .withAbility(superheroEntity.getAbility())
                .withUniverse(superheroEntity.getUniverse()));
    }

    public List<Superhero> getAll() {
        List<SuperheroEntity> listSuperheros = superheroRepository.findAll();

        return listSuperheros.stream()
                .map(s -> new Superhero()
                        .withId(s.getId())
                        .withName(s.getName())
                        .withAbility(s.getAbility())
                        .withUniverse(s.getUniverse()))
                .collect(Collectors.toList());
    }

    public List<Superhero> filterByName(String search) {
        List<SuperheroEntity> listSuperheros = superheroRepository.findByNameContainingIgnoreCase(search);

        return listSuperheros.stream()
                .map(s -> new Superhero()
                        .withId(s.getId())
                        .withName(s.getName())
                        .withAbility(s.getAbility())
                        .withUniverse(s.getUniverse()))
                .collect(Collectors.toList());
    }

    public Superhero create(Superhero superhero) {
        SuperheroEntity entity = new SuperheroEntity();
        entity.setName(superhero.getName());
        entity.setAbility(superhero.getAbility());
        entity.setUniverse(superhero.getUniverse());
        SuperheroEntity saved = superheroRepository.save(entity);
        return new Superhero()
                .withId(saved.getId())
                .withName(saved.getName())
                .withAbility(saved.getAbility())
                .withUniverse(saved.getUniverse());
    }

    public Superhero update(Long id, Superhero superhero) {
        SuperheroEntity existingSuperhero = superheroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Superhero not found"));

        String name = superhero.getName();
        if (Objects.nonNull(name)) {
            existingSuperhero.setName(name);
        }

        String ability = superhero.getAbility();
        if (Objects.nonNull(ability)) {
            existingSuperhero.setAbility(ability);
        }

        String universe = superhero.getUniverse();
        if (Objects.nonNull(universe)) {
            existingSuperhero.setUniverse(universe);
        }

        SuperheroEntity savedSuperhero = superheroRepository.save(existingSuperhero);

        return new Superhero()
                .withId(savedSuperhero.getId())
                .withName(savedSuperhero.getName())
                .withAbility(savedSuperhero.getAbility())
                .withUniverse(savedSuperhero.getUniverse());
    }

    public void delete(Long id) {
        superheroRepository.deleteById(id);
    }

}
