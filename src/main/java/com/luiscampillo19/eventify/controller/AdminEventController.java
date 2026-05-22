package com.luiscampillo19.eventify.controller;

import com.luiscampillo19.eventify.model.Event;
import com.luiscampillo19.eventify.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de vista MVC para el panel administrativo de Eventos.
 * Separa las rutas de UI (/admin/events) de las rutas de API (/api/events).
 * Aplica el patrón Post-Redirect-Get en el guardado.
 */
@Controller
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService eventService;

    public AdminEventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Escenarios 1 y 2: lista todos los eventos.
     * Model lleva la lista para th:each / th:if en la vista.
     */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("events", eventService.listarTodos());
        return "admin/events/list";
    }

    /**
     * Muestra el formulario vacío con un Event nuevo para th:object.
     */
    @GetMapping("/new")
    public String nuevo(Model model) {
        model.addAttribute("event", new Event());
        return "admin/events/form";
    }

    /**
     * Escenario 3: recibe el formulario vía @ModelAttribute,
     * persiste y redirige (Post-Redirect-Get).
     */
    @PostMapping
    public String guardar(@ModelAttribute Event event) {
        eventService.registrar(event);
        return "redirect:/admin/events";
    }
}
