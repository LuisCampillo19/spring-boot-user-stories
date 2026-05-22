package com.luiscampillo19.eventify.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de integración de vista – AdminVenueController.
 *
 * Verifica el Escenario 4: status 200 OK, nombre de vista correcto
 * y atributos esperados en el Model.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("AdminVenueController – Pruebas MockMvc")
class AdminVenueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /admin/venues → 200 OK, vista 'admin/venues/list', model contiene 'venues'")
    void listar_debeRetornar200ConVistaListaYAtributoVenues() throws Exception {
        mockMvc.perform(get("/admin/venues"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/venues/list"))
                .andExpect(model().attributeExists("venues"));
    }

    @Test
    @DisplayName("GET /admin/venues/new → 200 OK, vista 'admin/venues/form', model contiene 'venue'")
    void nuevo_debeRetornar200ConVistaFormularioYAtributoVenue() throws Exception {
        mockMvc.perform(get("/admin/venues/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/venues/form"))
                .andExpect(model().attributeExists("venue"));
    }

    @Test
    @DisplayName("POST /admin/venues → 3xx redirect a /admin/venues (Post-Redirect-Get)")
    void guardar_debeRedirigirAlListadoTrasGuardar() throws Exception {
        mockMvc.perform(post("/admin/venues")
                        .param("nombre", "Centro de Convenciones MockMvc")
                        .param("direccion", "Calle 100 # 50-10, Medellín")
                        .param("capacidad", "300"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/venues"));
    }
}
