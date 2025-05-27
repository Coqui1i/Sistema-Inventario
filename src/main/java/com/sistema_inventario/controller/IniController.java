package com.sistema_inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IniController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
