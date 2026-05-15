package com.luiscampillo19.eventify.service;

import com.luiscampillo19.eventify.exception.ResourceNotFoundException;
import com.luiscampillo19.eventify.model.Event;
import com.luiscampillo19.eventify.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event registrar(Event event) {
        if (event.getNombre() == null || event.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del evento no puede estar vacío.");
        }
        return eventRepository.save(event);
    }

    public List<Event> listarTodos() {
        return eventRepository.findAll();
    }

    public Page<Event> listarPaginado(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public Event obtenerPorId(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Evento no encontrado con ID: " + id));
    }

    public Event actualizar(Long id, Event datos) {
        Event existente = obtenerPorId(id);
        if (datos.getNombre() == null || datos.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del evento no puede estar vacío.");
        }
        existente.setNombre(datos.getNombre());
        existente.setFecha(datos.getFecha());
        existente.setDescripcion(datos.getDescripcion());
        return eventRepository.save(existente);
    }

    public void eliminar(Long id) {
        eventRepository.delete(obtenerPorId(id));
    }
}
