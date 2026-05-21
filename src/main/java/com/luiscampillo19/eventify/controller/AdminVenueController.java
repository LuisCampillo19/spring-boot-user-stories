package com.luiscampillo19.eventify.controller;

import com.luiscampillo19.eventify.model.Venue;
import com.luiscampillo19.eventify.service.VenueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de vista MVC para el panel administrativo de Lugares.
 * Separa las rutas de UI (/admin/venues) de las rutas de API (/api/venues).
 */
@Controller
@RequestMapping("/admin/venues")
public class AdminVenueController {

    private final VenueService venueService;

    public AdminVenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    /**
     * Lista todos los lugares. Model lleva la lista para la vista.
     */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("venues", venueService.listarTodos());
        return "admin/venues/list";
    }

    /**
     * Muestra el formulario vacío con un Venue nuevo para th:object.
     */
    @GetMapping("/new")
    public String nuevo(Model model) {
        model.addAttribute("venue", new Venue());
        return "admin/venues/form";
    }

    /**
     * Recibe el formulario, persiste el lugar y redirige (Post-Redirect-Get).
     */
    @PostMapping
    public String guardar(@ModelAttribute Venue venue) {
        venueService.registrar(venue);
        return "redirect:/admin/venues";
    }
}
