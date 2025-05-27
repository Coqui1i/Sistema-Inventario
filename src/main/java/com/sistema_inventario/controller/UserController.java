package com.sistema_inventario.controller;

import com.sistema_inventario.entity.UserEntity;
import com.sistema_inventario.service.CustomUserDetailsService;
import com.sistema_inventario.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/update")
    public String updateUser(@RequestParam String username, @RequestParam String firstName, @RequestParam String lastName) {
        UserEntity user = userService.findByUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam String username) {
        UserEntity user = userService.findByUsername(username);
        userService.deleteUser(user.getId());
        return "redirect:/admin/users";
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/verificar/{username}")
    public String verificarUsuario(@PathVariable String username) {
        customUserDetailsService.verificarUsuario(username);
        return "Verificación completada. Revisa la consola para ver el resultado.";
    }
}