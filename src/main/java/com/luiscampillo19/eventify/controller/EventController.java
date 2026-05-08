package com.luiscampillo19.eventify.controller;

import com.luiscampillo19.eventify.model.Event;
import com.luiscampillo19.eventify.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Eventos", description = "Gestión del catálogo de eventos")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @Operation(
        summary = "Registrar un nuevo evento",
        description = "Crea un evento en el catálogo. Retorna 201 Created con el objeto registrado."
    )
    public ResponseEntity<Event> registrar(@RequestBody Event event) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventService.registrar(event));
    }

    @GetMapping
    @Operation(
        summary = "Listar todos los eventos",
        description = "Retorna el catálogo completo. Si no hay registros, devuelve lista vacía con 200 OK."
    )
    public ResponseEntity<List<Event>> listar() {
        return ResponseEntity.ok(eventService.listarTodos());
    }
}
