package com.luiscampillo19.eventify.service;

import com.luiscampillo19.eventify.exception.ResourceNotFoundException;
import com.luiscampillo19.eventify.model.Venue;
import com.luiscampillo19.eventify.repository.VenueRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Venue registrar(Venue venue) {
        if (venue.getNombre() == null || venue.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del lugar no puede estar vacío.");
        }
        if (venue.getCapacidad() != null && venue.getCapacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser un número positivo.");
        }
        return venueRepository.save(venue);
    }

    public List<Venue> listarTodos() {
        return venueRepository.findAll();
    }

    public Page<Venue> listarPaginado(Pageable pageable) {
        return venueRepository.findAll(pageable);
    }

    public Venue obtenerPorId(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lugar no encontrado con ID: " + id));
    }

    public Venue actualizar(Long id, Venue datos) {
        Venue existente = obtenerPorId(id);
        if (datos.getNombre() == null || datos.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del lugar no puede estar vacío.");
        }
        if (datos.getCapacidad() != null && datos.getCapacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser un número positivo.");
        }
        existente.setNombre(datos.getNombre());
        existente.setDireccion(datos.getDireccion());
        existente.setCapacidad(datos.getCapacidad());
        return venueRepository.save(existente);
    }

    public void eliminar(Long id) {
        venueRepository.delete(obtenerPorId(id));
    }
}
