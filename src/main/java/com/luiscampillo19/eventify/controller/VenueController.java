package com.luiscampillo19.eventify.controller;

import com.luiscampillo19.eventify.model.Venue;
import com.luiscampillo19.eventify.service.VenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/venues")
@Tag(name = "Lugares", description = "Gestión del catálogo de lugares (venues)")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo lugar",
               description = "Crea un venue con validación de nombre y capacidad. Retorna 201 Created.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Lugar creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Venue> registrar(@RequestBody Venue venue) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(venueService.registrar(venue));
    }

    @GetMapping
    @Operation(
        summary = "Listar lugares paginados",
        description = "Soporta paginación y ordenamiento. Ejemplo: ?page=0&size=10&sort=nombre,asc"
    )
    @ApiResponse(responseCode = "200", description = "Lista paginada con metadatos")
    public ResponseEntity<Page<Venue>> listar(
            @Parameter(description = "Paginación: ?page=0&size=10&sort=nombre,asc")
            @PageableDefault(size = 10, sort = "nombre", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(venueService.listarPaginado(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener lugar por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lugar encontrado"),
        @ApiResponse(responseCode = "404", description = "Lugar no encontrado")
    })
    public ResponseEntity<Venue> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(venueService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un lugar existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lugar actualizado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Lugar no encontrado")
    })
    public ResponseEntity<Venue> actualizar(@PathVariable Long id, @RequestBody Venue venue) {
        return ResponseEntity.ok(venueService.actualizar(id, venue));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un lugar")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Lugar eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Lugar no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        venueService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
