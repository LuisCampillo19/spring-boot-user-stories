package com.luiscampillo19.eventify.repository;

import com.luiscampillo19.eventify.model.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventRepository {

    private final List<Event> events = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    public Event save(Event event) {
        event.setId(idCounter.incrementAndGet());
        events.add(event);
        return event;
    }

    public List<Event> findAll() {
        return new ArrayList<>(events);
    }

    public Optional<Event> findById(Long id) {
        return events.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }
}
