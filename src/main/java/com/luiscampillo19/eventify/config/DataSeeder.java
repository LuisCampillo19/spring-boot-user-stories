package com.luiscampillo19.eventify.config;

import com.luiscampillo19.eventify.model.Event;
import com.luiscampillo19.eventify.model.Venue;
import com.luiscampillo19.eventify.service.EventService;
import com.luiscampillo19.eventify.service.VenueService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

/**
 * Seeder de datos iniciales.
 * Registra como @Bean para que Spring lo ejecute al arrancar la aplicación.
 */
@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedData(EventService eventService, VenueService venueService) {
        return args -> {
            venueService.registrar(Venue.builder()
                    .nombre("Centro de Convenciones Medellín")
                    .direccion("Calle 100 # 50-35, Medellín")
                    .capacidad(1000)
                    .build());

            venueService.registrar(Venue.builder()
                    .nombre("Teatro Metropolitano")
                    .direccion("Calle 41 # 57-30, Medellín")
                    .capacidad(500)
                    .build());

            eventService.registrar(Event.builder()
                    .nombre("TechConf 2026")
                    .fecha(LocalDate.of(2026, 9, 15))
                    .descripcion("Conferencia de tecnología e innovación en Medellín")
                    .build());

            eventService.registrar(Event.builder()
                    .nombre("Festival Cultural Antioqueño")
                    .fecha(LocalDate.of(2026, 10, 20))
                    .descripcion("Festival de música, arte y cultura de Antioquia")
                    .build());

            System.out.println("[DataSeeder] Datos iniciales cargados correctamente.");
        };
    }
}
