package com.luiscampillo19.eventify.service;

import com.luiscampillo19.eventify.model.Event;
import com.luiscampillo19.eventify.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventService – Pruebas unitarias (sin contexto Spring)")
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event eventoValido;

    @BeforeEach
    void setUp() {
        eventoValido = Event.builder()
                .nombre("TechConf 2026")
                .fecha(LocalDate.of(2026, 9, 15))
                .descripcion("Conferencia tecnológica")
                .build();
    }

    // ── Escenario 1: Camino feliz ───────────────────────────────

    @Test
    @DisplayName("Debe registrar evento con datos válidos y retornar objeto con ID")
    void debeRegistrarEventoConDatosValidos() {
        Event guardado = Event.builder().id(1L).nombre("TechConf 2026")
                .fecha(LocalDate.of(2026, 9, 15)).descripcion("Conferencia").build();
        when(eventRepository.save(any(Event.class))).thenReturn(guardado);

        Event resultado = eventService.registrar(eventoValido);

        assertNotNull(resultado, "El resultado no debe ser nulo");
        assertEquals(1L, resultado.getId());
        assertEquals("TechConf 2026", resultado.getNombre());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    // ── Escenario 2: Camino de error ────────────────────────────

    @Test
    @DisplayName("Debe lanzar excepción cuando el nombre está vacío")
    void debeLanzarExcepcionNombreVacio() {
        eventoValido.setNombre("");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> eventService.registrar(eventoValido)
        );

        assertEquals("El nombre del evento no puede estar vacío.", ex.getMessage());
        verify(eventRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el nombre es nulo")
    void debeLanzarExcepcionNombreNulo() {
        eventoValido.setNombre(null);

        assertThrows(IllegalArgumentException.class, () -> eventService.registrar(eventoValido));
        verify(eventRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el nombre es solo espacios en blanco")
    void debeLanzarExcepcionNombreSoloEspacios() {
        eventoValido.setNombre("   ");

        assertThrows(IllegalArgumentException.class, () -> eventService.registrar(eventoValido));
        verify(eventRepository, never()).save(any());
    }

    // ── Escenario 3: Caso de borde – catálogo vacío ─────────────

    @Test
    @DisplayName("Debe retornar lista vacía con 200 cuando no hay eventos")
    void debeRetornarListaVaciaCuandoCatalogoEstaVacio() {
        when(eventRepository.findAll()).thenReturn(Collections.emptyList());

        List<Event> resultado = eventService.listarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty(), "Catálogo vacío debe retornar lista vacía, no null");
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar todos los eventos cuando hay registros")
    void debeRetornarTodosLosEventos() {
        List<Event> eventos = List.of(
                Event.builder().id(1L).nombre("Evento A").fecha(LocalDate.now()).build(),
                Event.builder().id(2L).nombre("Evento B").fecha(LocalDate.now()).build()
        );
        when(eventRepository.findAll()).thenReturn(eventos);

        List<Event> resultado = eventService.listarTodos();

        assertEquals(2, resultado.size());
    }
}
