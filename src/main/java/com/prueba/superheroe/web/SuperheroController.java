package com.prueba.superheroe.web;

import com.prueba.superheroe.model.dto.Superhero;
import com.prueba.superheroe.service.SuperheroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/superheros")
public class SuperheroController {

    private final Logger LOG = LoggerFactory.getLogger(SuperheroController.class);

    @Autowired
    private SuperheroService superheroService;

    @GetMapping("/{id}")
    public ResponseEntity<Superhero> getOne(@PathVariable Long id) {
        LOG.info("Searching by Id");
        Optional<Superhero> superhero = superheroService.getOne(id);
        return superhero.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Superhero>> getAll() {
        LOG.info("Searching all");
        List<Superhero> superheros = superheroService.getAll();
        return ResponseEntity.ok(superheros);
    }

    @GetMapping(value = "/search", params = {"name"})
    public ResponseEntity<List<Superhero>> filterByName(@RequestParam("name") String search) {
        LOG.info("Search by String in name");
        List<Superhero> superheros = superheroService.filterByName(search);
        return ResponseEntity.ok(superheros);
    }

    @PostMapping("/")
    public ResponseEntity<Superhero> createSuperhero(@RequestBody Superhero superhero) {
        LOG.info("Creating");
        Superhero createdSuperhero = superheroService.create(superhero);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSuperhero);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Superhero> update(@PathVariable Long id, @RequestBody Superhero superhero) {
        LOG.info("Updating");
        Superhero updatedSuperhero = superheroService.update(id, superhero);
        if (Objects.nonNull(updatedSuperhero)) {
            return ResponseEntity.ok(updatedSuperhero);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        LOG.info("Deleting");
        superheroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
