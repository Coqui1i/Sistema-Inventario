package com.sistema_inventario.controller;

import com.sistema_inventario.entity.RoleEntity;
import com.sistema_inventario.entity.UserEntity;
import com.sistema_inventario.service.RoleService;
import com.sistema_inventario.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String first_name, @RequestParam String last_name,
                               @RequestParam String username, @RequestParam String password,
                               RedirectAttributes redirectAttributes) {
        try {

            // Verificar si el nombre de usuario ya existe
            if (userService.existsByUsername(username)) {
                redirectAttributes.addFlashAttribute("errorMessage", "El nombre de usuario ya está en uso.");
                return "redirect:/register"; // Redirigir a la página de registro
            }

            // Crear y configurar el nuevo usuario
            UserEntity newUser = new UserEntity();
            newUser.setUsername(username);
            newUser.setPassword(passwordEncoder.encode(password));
            newUser.setFirstName(first_name);
            newUser.setLastName(last_name);

            RoleEntity userRole = roleService.findByName("ROLE_USER");
            newUser.addRole(userRole); // Agregar el rol al usuario

            userService.saveUser(newUser);

            return "redirect:/login";
        } catch (Exception e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al registrar el usuario: " + e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "register";
    }
}