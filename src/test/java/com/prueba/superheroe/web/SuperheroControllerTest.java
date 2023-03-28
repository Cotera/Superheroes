package com.prueba.superheroe.web;

import com.prueba.superheroe.model.dto.Superhero;
import com.prueba.superheroe.service.SuperheroService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(SuperheroController.class)
class SuperheroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuperheroService superheroService;

    @Test
    void getOne() throws Exception {
        Long givenId = 1L;
        Superhero givenSuperhero = new Superhero();
        givenSuperhero.setId(givenId);
        givenSuperhero.setName("Ironman");
        givenSuperhero.setAbility("Genio, Millonario, Playboy, Filantropo");
        givenSuperhero.setUniverse("Marvel");
        when(superheroService.getOne(givenId)).thenReturn(givenSuperhero);

        mockMvc.perform(MockMvcRequestBuilders.get("/superheres/{id}", givenId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(readFile("src/test/resources/json/getOneTest.json")));
    }

    @Test
    void getAll() throws Exception {
        Superhero superhero1 = new Superhero();
        superhero1.setId(1L);
        superhero1.setName("Ironman");
        superhero1.setAbility("Genio, Millonario, Playboy, Filantropo");
        superhero1.setUniverse("Marvel");

        Superhero superhero2 = new Superhero();
        superhero2.setId(2L);
        superhero2.setName("Spiderman");
        superhero2.setAbility("Fuerza, agilidad y telarañas");
        superhero2.setUniverse("Marvel");

        List<Superhero> givenSuperheros = List.of(superhero1, superhero2);
        when(superheroService.getAll()).thenReturn(givenSuperheros);

        mockMvc.perform(MockMvcRequestBuilders.get("/superheros"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(readFile("src/test/resources/json/getAllTest.json")));
    }

    @Test
    void filterByName() throws Exception {
        String givenSearch = "Iron";

        Superhero givenSuperhero = new Superhero();
        givenSuperhero.setId(1L);
        givenSuperhero.setName("Ironman");
        givenSuperhero.setAbility("Genio, Millonario, Playboy, Filantropo");
        givenSuperhero.setUniverse("Marvel");
        List<Superhero> superheros = List.of(givenSuperhero);

        when(superheroService.filterByName(givenSearch)).thenReturn(superheros);

        mockMvc.perform(MockMvcRequestBuilders.get("/superheros/search?name=" + givenSearch))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(readFile("src/test/resources/json/getOneTest.json")));
    }

    @Test
    void update() throws Exception {
        Long givenId = 1L;
        Superhero givenSuperhero = new Superhero();
        givenSuperhero.setName("Ironman");
        givenSuperhero.setAbility("Genio, Millonario, Playboy, Filantropo");
        givenSuperhero.setUniverse("Marvel");

        doNothing().when(superheroService).update(givenId, givenSuperhero);

        mockMvc.perform(MockMvcRequestBuilders.put("/superheros/{id}", givenId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(readFile("src/test/resources/json/getOneTest.json")));

        verify(superheroService).update(eq(givenId), eq(givenSuperhero));
    }

    @Test
    void delete() throws Exception {
        Long givenId = 1L;

        doNothing().when(superheroService).delete(givenId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/superheros/{id}", givenId))
                .andExpect(status().isOk());

        verify(superheroService).delete(eq(givenId));
    }

    private String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}