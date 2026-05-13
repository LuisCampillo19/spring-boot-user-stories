package com.luiscampillo19.eventify.controller;

import com.luiscampillo19.eventify.model.Event;
import com.luiscampillo19.eventify.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Eventos", description = "Gestión del catálogo de eventos")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo evento")
    public ResponseEntity<Event> registrar(@RequestBody Event event) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventService.registrar(event));
    }

    @GetMapping
    @Operation(summary = "Listar eventos con paginación y ordenamiento")
    public ResponseEntity<Page<Event>> listar(
            @PageableDefault(size = 10, sort = "nombre", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(eventService.listarPaginado(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener evento por ID")
    public ResponseEntity<Event> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un evento existente")
    public ResponseEntity<Event> actualizar(@PathVariable Long id, @RequestBody Event event) {
        return ResponseEntity.ok(eventService.actualizar(id, event));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un evento")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eventService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
