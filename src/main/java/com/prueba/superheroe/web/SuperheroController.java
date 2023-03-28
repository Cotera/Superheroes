package com.prueba.superheroe.web;

import com.prueba.superheroe.model.dto.Superhero;
import com.prueba.superheroe.service.SuperheroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    /**
     * Devuelve un superheroe que coincida con el Id dado
     *
     * @param id Id a buscar
     * @return Superheroe encontrado o vacío
     */
    @Cacheable(value = "superheroes")
    @GetMapping("/{id}")
    public ResponseEntity<Superhero> getOne(@PathVariable Long id) {
        LOG.info("Searching by Id");
        Optional<Superhero> superhero = superheroService.getOne(id);
        return superhero.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Devuelve todos los superheroes de la base de datos
     *
     * @return Lista de superheroes
     */
    @Cacheable(value = "superheroes")
    @GetMapping
    public ResponseEntity<List<Superhero>> getAll() {
        LOG.info("Searching all");
        List<Superhero> superheros = superheroService.getAll();
        return ResponseEntity.ok(superheros);
    }

    /**
     * Devuelve la lista de superheroes que contenga una cadena en el nombre
     *
     * @param search cadena a buscar
     * @return Lista de superheroes encontrados
     */
    @Cacheable(value = "superheroes")
    @GetMapping(value = "/search", params = {"name"})
    public ResponseEntity<List<Superhero>> filterByName(@RequestParam("name") String search) {
        LOG.info("Search by String in name");
        List<Superhero> superheros = superheroService.filterByName(search);
        return ResponseEntity.ok(superheros);
    }

    /**
     * Crea un nuevo superheroe
     *
     * @param superhero Superheroe que se va a crear
     * @return Superheroe creado
     */
    @CacheEvict(value = "superheroes")
    @PostMapping("/")
    public ResponseEntity<Superhero> createSuperhero(@RequestBody Superhero superhero) {
        LOG.info("Creating");
        Superhero createdSuperhero = superheroService.create(superhero);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSuperhero);
    }

    /**
     * Actualiza un superheroe. Si algun campo es null se mantiene el valor actual
     *
     * @param id Id del heroe a actualizar
     * @param superhero Datos para actualizar el superheroe
     * @return Superheroe actualizado
     */
    @CacheEvict(value = "superheroes")
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

    /**
     * Elimina un superheroe basado en su Id
     *
     * @param id Id del superheroe a eliminar
     */
    @CacheEvict(value = "superheroes")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        LOG.info("Deleting");
        superheroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
