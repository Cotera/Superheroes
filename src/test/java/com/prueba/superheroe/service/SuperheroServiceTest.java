package com.prueba.superheroe.service;

import com.prueba.superheroe.model.SuperheroEntity;
import com.prueba.superheroe.model.dto.Superhero;
import com.prueba.superheroe.repository.SuperheroRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class SuperheroServiceTest {

    @Mock
    private SuperheroRepository superheroRepository;

    @InjectMocks
    private SuperheroService tested;

    @Test
    void getOne() {
        //Given
        long givenId = 1L;
        String givenName = "Ironman";
        String givenAbility = "Genio, Millonario, Playboy, Filantropo";
        String givenUniverse = "Marvel";

        SuperheroEntity givenSuperhero = buildSuperheroEntity(givenId, givenName, givenAbility, givenUniverse);
        when(superheroRepository.findById(givenId)).thenReturn(Optional.of(givenSuperhero));

        Superhero expectedSuperhero = buildSuperhero(givenId, givenName, givenAbility, givenUniverse);

        //When
        Superhero actualResponse = tested.getOne(givenId);

        //Then
        assertEquals(expectedSuperhero.getId(), actualResponse.getId());
        assertEquals(expectedSuperhero.getName(), actualResponse.getName());
        assertEquals(expectedSuperhero.getAbility(), actualResponse.getAbility());
        assertEquals(expectedSuperhero.getUniverse(), actualResponse.getUniverse());
    }

    @Test
    void getAll() {
        //Given
        SuperheroEntity givenSuper1 = buildSuperheroEntity(1L, "Ironman", "Genio, Millonario, Playboy, Filantropo", "Marvel");
        SuperheroEntity givenSuper2 = buildSuperheroEntity(1L, "Superman", "Fuerza,Velocidad,Rayos", "DC");

        List<SuperheroEntity> givenSuperheroes = List.of(givenSuper1,givenSuper2);
        when(superheroRepository.findAll()).thenReturn(givenSuperheroes);

        Superhero expectedSuper1 = buildSuperhero(1L, "Ironman", "Genio, Millonario, Playboy, Filantropo", "Marvel");
        Superhero expectedSuper2 = buildSuperhero(1L, "Superman", "Fuerza,Velocidad,Rayos", "DC");
        List<Superhero> expectedSuperheroes = List.of(expectedSuper1, expectedSuper2);

        //When
        List<Superhero> actualResponse = tested.getAll();

        //Then
        assertEquals(expectedSuperheroes.size(), actualResponse.size());

        assertEquals(expectedSuper1.getName(), actualResponse.get(0).getName());
        assertEquals(expectedSuper2.getName(), actualResponse.get(1).getName());
    }

    @Test
    void filterByName() {
        //Given
        String search = "iron";

        long givenId = 1L;
        String givenName = "Ironman";
        String givenAbility = "Genio, Millonario, Playboy, Filantropo";
        String givenUniverse = "Marvel";

        SuperheroEntity givenSuperhero = buildSuperheroEntity(givenId, givenName, givenAbility, givenUniverse);
        when(superheroRepository.findByNameContainingIgnoreCase(search)).thenReturn(List.of(givenSuperhero));

        Superhero expectedSuperhero = buildSuperhero(givenId, givenName, givenAbility, givenUniverse);

        //When
        List<Superhero> actualResponse = tested.filterByName(search);

        //Then
        assertEquals(expectedSuperhero.getId(), actualResponse.get(0).getId());
        assertEquals(expectedSuperhero.getName(), actualResponse.get(0).getName());
        assertEquals(expectedSuperhero.getAbility(), actualResponse.get(0).getAbility());
        assertEquals(expectedSuperhero.getUniverse(), actualResponse.get(0).getUniverse());
    }

    @Test
    void update() {
        //Given
        long givenId = 1L;
        String givenName = "Ironman";
        String givenAbility = "Genio, Millonario, Playboy, Filantropo";
        String givenUniverse = "Marvel";

        SuperheroEntity givenEntity = buildSuperheroEntity(givenId, givenName, givenAbility, givenUniverse);
        when(superheroRepository.save(givenEntity)).thenReturn(givenEntity);

        Superhero givenSuperhero = buildSuperhero(null, givenName, givenAbility, givenUniverse);

        Superhero expectedSuperhero = buildSuperhero(givenId, givenName, givenAbility, givenUniverse);

        //When
        Superhero actualResponse = tested.update(givenId, givenSuperhero);

        //Then
        assertEquals(expectedSuperhero.getId(), actualResponse.getId());
        assertEquals(expectedSuperhero.getName(), actualResponse.getName());
        assertEquals(expectedSuperhero.getAbility(), actualResponse.getAbility());
        assertEquals(expectedSuperhero.getUniverse(), actualResponse.getUniverse());
    }

    @Test
    void delete() {
        //Given
        long givenId = 1L;
        doNothing().when(superheroRepository).deleteById(givenId);
        //When
        tested.delete(givenId);
        //Then
        verify(superheroRepository, times(1)).deleteById(givenId);
        verifyNoMoreInteractions(superheroRepository);
    }

    private SuperheroEntity buildSuperheroEntity (Long id,
                                                  String name,
                                                  String ability,
                                                  String universe) {
        SuperheroEntity entity = new SuperheroEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setAbility(ability);
        entity.setUniverse(universe);
        return entity;
    }

    private Superhero buildSuperhero (Long id,
                                      String name,
                                      String ability,
                                      String universe)  {
        Superhero entity = new Superhero();
        entity.setId(id);
        entity.setName(name);
        entity.setAbility(ability);
        entity.setUniverse(universe);
        return entity;
    }
}