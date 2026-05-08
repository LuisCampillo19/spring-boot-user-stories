package com.luiscampillo19.eventify.controller;

import com.luiscampillo19.eventify.model.Venue;
import com.luiscampillo19.eventify.service.VenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
@Tag(name = "Lugares", description = "Gestión del catálogo de lugares (venues)")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping
    @Operation(
        summary = "Registrar un nuevo lugar",
        description = "Crea un venue en el catálogo. Retorna 201 Created con el objeto registrado."
    )
    public ResponseEntity<Venue> registrar(@RequestBody Venue venue) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(venueService.registrar(venue));
    }

    @GetMapping
    @Operation(
        summary = "Listar todos los lugares",
        description = "Retorna el catálogo completo. Si no hay registros, devuelve lista vacía con 200 OK."
    )
    public ResponseEntity<List<Venue>> listar() {
        return ResponseEntity.ok(venueService.listarTodos());
    }
}
