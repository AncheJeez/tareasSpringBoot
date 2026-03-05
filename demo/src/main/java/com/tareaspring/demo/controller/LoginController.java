package com.tareaspring.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        if (model.containsAttribute("error")) {
            model.addAttribute("error", "Credenciales incorrectas. Intenta nuevamente.");
        }
        return "login";
    }
}