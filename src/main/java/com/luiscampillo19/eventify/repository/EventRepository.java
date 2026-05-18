package com.luiscampillo19.eventify.repository;

import com.luiscampillo19.eventify.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Consulta derivada: busca eventos cuyo nombre contenga el texto indicado.
     * Equivale a: SELECT * FROM events WHERE nombre LIKE '%nombre%'
     */
    List<Event> findByNombreContaining(String nombre);
}
