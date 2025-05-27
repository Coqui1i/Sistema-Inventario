package com.sistema_inventario.controller;

import com.sistema_inventario.entity.UserEntity;
import com.sistema_inventario.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        try {
            UserEntity user = userService.findByUsername(username);
            
            if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Nombre de usuario o contraseña incorrectos.");
                return "redirect:/login";
            }
            
            if (!user.isEnabled()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Tu cuenta está deshabilitada.");
                return "redirect:/login";
            }
            
            return "redirect:/home";
        } catch (UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nombre de usuario o contraseña incorrectos.");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response,
                        RedirectAttributes attributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        attributes.addFlashAttribute("mensajeLogout", "Has cerrado sesión exitosamente");
        return "redirect:/login?logout";
    }

    @GetMapping("/access-denied")
    public String accesoDenegado(Model model) {
        model.addAttribute("mensaje", "No tienes permisos para acceder a esta página");
        return "error/403";
    }

    @GetMapping("/profile")
    public String verPerfil(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("roles", authentication.getAuthorities());

            return "profile";
        }
        return "redirect:/login";
    }

}