package com.luiscampillo19.eventify.repository;

import com.luiscampillo19.eventify.model.Venue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("VenueRepository – Pruebas de integración con @DataJpaTest")
class VenueRepositoryTest {

    @Autowired
    private VenueRepository venueRepository;

    @Test
    @DisplayName("Debe persistir y recuperar un venue por ID")
    void debeGuardarYRecuperarVenuePorId() {
        Venue venue = crearVenue("Teatro Metropolitano", "Calle 41 # 57-30, Medellín", 500);

        Venue guardado = venueRepository.save(venue);

        assertNotNull(guardado.getId());
        Optional<Venue> recuperado = venueRepository.findById(guardado.getId());

        assertTrue(recuperado.isPresent());
        assertEquals("Teatro Metropolitano", recuperado.get().getNombre());
        assertEquals(500, recuperado.get().getCapacidad());
    }

    @Test
    @DisplayName("Debe encontrar venues con consulta derivada findByNombreContaining")
    void debeEncontrarVenuesPorNombreParcial() {
        venueRepository.save(crearVenue("Teatro Metropolitano", "Calle 41", 500));
        venueRepository.save(crearVenue("Teatro de la Ciudad", "Carrera 10", 300));
        venueRepository.save(crearVenue("Centro de Convenciones", "Calle 100", 1000));

        List<Venue> resultados = venueRepository.findByNombreContaining("Teatro");

        assertEquals(2, resultados.size());
        assertTrue(resultados.stream().allMatch(v -> v.getNombre().contains("Teatro")));
    }

    @Test
    @DisplayName("Debe retornar página paginada de venues")
    void debeRetornarPaginaDeVenues() {
        for (int i = 1; i <= 8; i++) {
            venueRepository.save(crearVenue("Venue " + i, "Dirección " + i, 100 * i));
        }

        Page<Venue> pagina = venueRepository.findAll(
                PageRequest.of(0, 3, Sort.by("nombre").ascending()));

        assertEquals(3, pagina.getContent().size());
        assertEquals(8, pagina.getTotalElements());
    }

    @Test
    @DisplayName("Debe eliminar venue y confirmar ausencia")
    void debeEliminarVenueCorrectamente() {
        Venue venue = venueRepository.save(crearVenue("Venue Temporal", "Calle 1", 50));

        venueRepository.deleteById(venue.getId());

        assertFalse(venueRepository.findById(venue.getId()).isPresent());
    }

    private Venue crearVenue(String nombre, String direccion, Integer capacidad) {
        return Venue.builder()
                .nombre(nombre)
                .direccion(direccion)
                .capacidad(capacidad)
                .build();
    }
}
