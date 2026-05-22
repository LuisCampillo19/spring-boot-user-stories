package com.luiscampillo19.eventify.repository;

import com.luiscampillo19.eventify.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    /**
     * Consulta derivada: busca venues cuyo nombre contenga el texto indicado.
     */
    List<Venue> findByNombreContaining(String nombre);
}
