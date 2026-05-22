package com.luiscampillo19.eventify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador raíz del panel administrativo.
 * Redirige /admin al dashboard principal.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping({"", "/"})
    public String index() {
        return "admin/index";
    }
}
