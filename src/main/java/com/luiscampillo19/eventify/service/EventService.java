package com.luiscampillo19.eventify.service;

import com.luiscampillo19.eventify.model.Event;
import com.luiscampillo19.eventify.repository.EventRepository;
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
}
