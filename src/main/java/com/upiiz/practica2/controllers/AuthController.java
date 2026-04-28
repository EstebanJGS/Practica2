package com.upiiz.practica2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.upiiz.practica2.models.Usuario;
import com.upiiz.practica2.services.EmailService;
import com.upiiz.practica2.services.UsuarioService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @GetMapping({"", "/", "/index"})
    public String rootRedirect() {
        return "redirect:/mascotas/listado_mascotas";
    }

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
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/mascotas/listado_mascotas";
        } else {
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "mascotas/auth/register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute Usuario usuario, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "mascotas/auth/register";
        }
        try {
            usuarioService.registrarUsuario(usuario);
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/register";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "mascotas/auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioService.buscarPorEmail(email);
            if (usuario != null) {
                emailService.enviarContrasena(usuario.getEmail(), usuario.getNombreCompleto(), usuario.getPassword());
                redirectAttributes.addFlashAttribute("success", "Se ha enviado tu contraseña al correo registrado.");
            } else {
                redirectAttributes.addFlashAttribute("error", "No existe una cuenta con ese correo.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al enviar el correo. Intenta más tarde.");
        }
        return "redirect:/forgot-password";
    }
}
