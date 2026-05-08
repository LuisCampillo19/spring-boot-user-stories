package com.luiscampillo19.eventify.service;

import com.luiscampillo19.eventify.model.Venue;
import com.luiscampillo19.eventify.repository.VenueRepository;
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
}
