package com.upiiz.practica2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.upiiz.practica2.models.Usuario;
import com.upiiz.practica2.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Correo o contraseña incorrectos.");
        }
        return "mascotas/auth/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioService.autenticar(email, password);
        if (usuario != null) {
            session.setAttribute("usuarioLogueado", usuario); // Guardamos el usuario en la sesión
            return "redirect:/mascotas/index";
        } else {
            redirectAttributes.addAttribute("error", "true");
            return "redirect:mascotas/auth/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destruimos la sesión
        return "redirect:mascotas/auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "mascotas/auth/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "mascotas/vistas/auth/forgot-password";
    }
}