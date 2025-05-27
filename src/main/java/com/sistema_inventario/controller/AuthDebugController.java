package com.sistema_inventario.controller;

import com.sistema_inventario.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthDebugController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthDebugController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/debug/encode")
    public String encodePassword(@RequestParam String password) {
        return userService.encodePassword(password);
    }

    @GetMapping("/debug/check-password")
    public String checkPassword(@RequestParam String rawPassword, @RequestParam String hashedPassword) {
        boolean matches = passwordEncoder.matches(rawPassword, hashedPassword);
        return "La contraseña " + (matches ? "COINCIDE" : "NO COINCIDE") + " con el hash";
    }
}
