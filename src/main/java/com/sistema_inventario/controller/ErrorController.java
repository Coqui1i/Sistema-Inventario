package com.sistema_inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @GetMapping("/error-category")
    public String errorPage() {
        return "error/error-category";
    }

    @GetMapping("/error-supplier")
    public String errorPageSupplier() {
        return "error/error-supplier";
    }

    @RequestMapping("/error/403")
    public String handle403() {
        return "error/403";
    }
}