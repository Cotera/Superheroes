package com.prueba.superheroe.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.superheroe.model.SuperheroEntity;
import com.prueba.superheroe.model.dto.Superhero;
import com.prueba.superheroe.repository.SuperheroRepository;
import com.prueba.superheroe.web.config.TestConfig;
import javafx.application.Application;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertFalse;


@SpringBootTest(classes = {Application.class, TestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class SuperheroIntegrationTest {

    @Autowired
    private SuperheroRepository superheroRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        SuperheroEntity hero1 = new SuperheroEntity();
        hero1.setName("Ironman");
        hero1.setAbility("Armadura tecnologica chetadisima");
        hero1.setUniverse("Marvel");
        superheroRepository.save(hero1);

        SuperheroEntity hero2 = new SuperheroEntity();
        hero2.setName("Flash");
        hero2.setAbility("Supervelocidad");
        hero2.setUniverse("DC");
        superheroRepository.save(hero2);
    }

    @Test
    public void testGetAllSuperheroes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/superheroes/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Ironman"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Flash"));
    }

    @Test
    public void testGetSuperheroById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/superheroes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Ironman"));
    }

    @Test
    public void testGetSuperheroByNameContaining() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/superheroes/search")
                .param("name", "man")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Spiderman"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Ironman"));
    }

    @Test
    public void testCreateSuperhero() throws Exception {
        Superhero superhero = new Superhero()
                .withName("Ironman")
                .withAbility("Armadura tecnologica chetadisima")
                .withUniverse("Marvel");

        mockMvc.perform(MockMvcRequestBuilders.post("/superheroes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(superhero)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(superhero.getName()));
    }

    @Test
    public void testUpdateSuperhero() throws Exception {
        Superhero superhero = new Superhero()
                .withAbility("Supervelocidad, viajes en el tiempo, viajes interdimesionales");

        mockMvc.perform(MockMvcRequestBuilders.put("/superheroes/{id}", superhero.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(superhero)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ability").value(superhero.getAbility()));
    }

    @Test
    public void testDeleteSuperhero() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/superheroes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertFalse(superheroRepository.findById(1L).isPresent());
    }
}

