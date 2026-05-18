package com.luiscampillo19.eventify.repository;

import com.luiscampillo19.eventify.model.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("EventRepository – Pruebas de integración con @DataJpaTest")
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    @DisplayName("Escenario 1 – Debe persistir y recuperar evento por ID")
    void debeGuardarYRecuperarEventoPorId() {
        Event evento = crearEvento("TechConf 2026", "Conferencia tecnológica");

        Event guardado = eventRepository.save(evento);

        assertNotNull(guardado.getId(), "El ID debe ser generado automáticamente por @GeneratedValue");
        Optional<Event> recuperado = eventRepository.findById(guardado.getId());

        assertTrue(recuperado.isPresent(), "El evento debe encontrarse por ID");
        assertEquals("TechConf 2026", recuperado.get().getNombre());
    }

    @Test
    @DisplayName("Debe encontrar eventos con consulta derivada findByNombreContaining")
    void debeEncontrarEventosPorNombreParcial() {
        eventRepository.save(crearEvento("TechConf 2026", "Conf"));
        eventRepository.save(crearEvento("Tech Summit Medellín", "Summit"));
        eventRepository.save(crearEvento("Festival Cultural", "Festival"));

        List<Event> resultados = eventRepository.findByNombreContaining("Tech");

        assertEquals(2, resultados.size(), "Debe encontrar exactamente 2 eventos con 'Tech'");
        assertTrue(resultados.stream().allMatch(e -> e.getNombre().contains("Tech")));
    }

    @Test
    @DisplayName("Escenario 3 – Debe retornar página con tamaño y metadatos correctos")
    void debeRetornarPaginaConTamanoYMetadatosCorrectos() {
        for (int i = 1; i <= 10; i++) {
            eventRepository.save(crearEvento("Evento " + i, "Desc " + i));
        }

        Page<Event> pagina = eventRepository.findAll(
                PageRequest.of(0, 5, Sort.by("nombre").ascending()));

        assertEquals(5, pagina.getContent().size(), "La página debe tener 5 elementos");
        assertEquals(10, pagina.getTotalElements(), "El total debe ser 10");
        assertEquals(2, pagina.getTotalPages(), "Deben existir 2 páginas");
        assertEquals(0, pagina.getNumber(), "Debe ser la página 0 (0-indexed)");
    }

    @Test
    @DisplayName("Escenario 4 – Debe eliminar evento y confirmar ausencia")
    void debeEliminarEventoCorrectamente() {
        Event evento = eventRepository.save(crearEvento("Evento Cancelado", "Temporal"));

        eventRepository.deleteById(evento.getId());

        assertFalse(eventRepository.findById(evento.getId()).isPresent(),
                "El evento no debe existir tras ser eliminado");
    }

    @Test
    @DisplayName("Escenario 2 – Debe retornar Optional vacío para ID inexistente (9999)")
    void debeRetornarOptionalVacioParaIdInexistente() {
        Optional<Event> resultado = eventRepository.findById(9999L);

        assertFalse(resultado.isPresent(), "ID 9999 no debe encontrar ningún evento");
    }

    private Event crearEvento(String nombre, String descripcion) {
        return Event.builder()
                .nombre(nombre)
                .fecha(LocalDate.of(2026, 10, 1))
                .descripcion(descripcion)
                .build();
    }
}
