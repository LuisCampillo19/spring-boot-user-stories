package com.luiscampillo19.eventify.repository;

import com.luiscampillo19.eventify.model.Venue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class VenueRepository {

    private final List<Venue> venues = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    public Venue save(Venue venue) {
        venue.setId(idCounter.incrementAndGet());
        venues.add(venue);
        return venue;
    }

    public List<Venue> findAll() {
        return new ArrayList<>(venues);
    }

    public Optional<Venue> findById(Long id) {
        return venues.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst();
    }
}
