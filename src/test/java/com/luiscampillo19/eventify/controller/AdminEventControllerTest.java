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
 * Pruebas de integración de vista – AdminEventController.
 *
 * Verifica el Escenario 4: status 200 OK, nombre de vista correcto
 * y atributos esperados en el Model.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("AdminEventController – Pruebas MockMvc")
class AdminEventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /admin/events → 200 OK, vista 'admin/events/list', model contiene 'events'")
    void listar_debeRetornar200ConVistaListaYAtributoEvents() throws Exception {
        mockMvc.perform(get("/admin/events"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/events/list"))
                .andExpect(model().attributeExists("events"));
    }

    @Test
    @DisplayName("GET /admin/events/new → 200 OK, vista 'admin/events/form', model contiene 'event'")
    void nuevo_debeRetornar200ConVistaFormularioYAtributoEvent() throws Exception {
        mockMvc.perform(get("/admin/events/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/events/form"))
                .andExpect(model().attributeExists("event"));
    }

    @Test
    @DisplayName("POST /admin/events → 3xx redirect a /admin/events (Post-Redirect-Get)")
    void guardar_debeRedirigirAlListadoTrasGuardar() throws Exception {
        mockMvc.perform(post("/admin/events")
                        .param("nombre", "Festival de Cine MockMvc")
                        .param("fecha", "2026-09-20")
                        .param("descripcion", "Evento generado por test de integración"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/events"));
    }
}
