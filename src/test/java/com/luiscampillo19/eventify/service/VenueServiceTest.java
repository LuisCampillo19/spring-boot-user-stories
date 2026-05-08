package com.luiscampillo19.eventify.service;

import com.luiscampillo19.eventify.model.Venue;
import com.luiscampillo19.eventify.repository.VenueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VenueService – Pruebas unitarias (sin contexto Spring)")
class VenueServiceTest {

    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private VenueService venueService;

    private Venue venueValido;

    @BeforeEach
    void setUp() {
        venueValido = Venue.builder()
                .nombre("Centro de Convenciones")
                .direccion("Calle 100 #50-35, Medellín")
                .capacidad(1000)
                .build();
    }

    @Test
    @DisplayName("Debe registrar un venue con datos válidos")
    void debeRegistrarVenueValido() {
        Venue guardado = Venue.builder().id(1L).nombre("Centro de Convenciones")
                .direccion("Calle 100").capacidad(1000).build();
        when(venueRepository.save(any(Venue.class))).thenReturn(guardado);

        Venue resultado = venueService.registrar(venueValido);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(venueRepository, times(1)).save(any(Venue.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el nombre está vacío")
    void debeLanzarExcepcionNombreVacio() {
        venueValido.setNombre("");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> venueService.registrar(venueValido)
        );

        assertEquals("El nombre del lugar no puede estar vacío.", ex.getMessage());
        verify(venueRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la capacidad es negativa")
    void debeLanzarExcepcionCapacidadNegativa() {
        venueValido.setCapacidad(-100);

        assertThrows(IllegalArgumentException.class, () -> venueService.registrar(venueValido));
        verify(venueRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la capacidad es cero")
    void debeLanzarExcepcionCapacidadCero() {
        venueValido.setCapacidad(0);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> venueService.registrar(venueValido)
        );
        assertEquals("La capacidad debe ser un número positivo.", ex.getMessage());
        verify(venueRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe retornar lista vacía cuando no hay venues")
    void debeRetornarListaVaciaConCatalogoVacio() {
        when(venueRepository.findAll()).thenReturn(Collections.emptyList());

        List<Venue> resultado = venueService.listarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}
