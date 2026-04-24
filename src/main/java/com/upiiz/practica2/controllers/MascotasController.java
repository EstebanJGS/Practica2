package com.upiiz.practica2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.upiiz.practica2.models.Mascota;
import com.upiiz.practica2.models.Usuario;
import com.upiiz.practica2.services.MascotaServicio;
import com.upiiz.practica2.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mascotas")
public class MascotasController {

    @Autowired
    private MascotaServicio mascotaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String auth(@RequestParam(required = false) String error, Model model) {
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
            return "redirect:/mascotas/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destruimos la sesión
        return "redirect:/mascotas/login";
    }

    @GetMapping("/register")
    public String register() {
        return "mascotas/auth/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "mascotas/auth/forgot-password";
    }

    @GetMapping("/index")
    public String index(Model model) {
        // Pasamos el total de mascotas en la Base de Datos a la vista
        model.addAttribute("totalMascotas", mascotaService.listarTodas().size());
        return "mascotas/index";
    }

    @GetMapping("/listado_mascotas")
    public String listadoMascotas(Model model) {
        model.addAttribute("mascotas", mascotaService.listarTodas());
        return "mascotas/vista/listado_mascotas";
    }

    @GetMapping("/agregar_mascota")
    public String agregarMascota() {
        return "mascotas/vista/agregar_mascota";
    }

    @PostMapping("/guardar")
    public String guardarMascota(@ModelAttribute Mascota mascota) {
        mascotaService.guardar(mascota);
        return "redirect:/mascotas/listado_mascotas";
    }

    @GetMapping("/editar_mascota/{id}")
    public String editarMascota(@PathVariable Long id, Model model) {
        model.addAttribute("mascota", mascotaService.buscarPorId(id));
        return "mascotas/vista/editar_mascota";
    }

    @PostMapping("/actualizar")
    public String actualizarMascota(@ModelAttribute Mascota mascota) {
        mascotaService.guardar(mascota);
        return "redirect:/mascotas/listado_mascotas";
    }

    @GetMapping("/eliminar_mascota/{id}")
    public String eliminarMascota(@PathVariable Long id, Model model) {
        model.addAttribute("mascota", mascotaService.buscarPorId(id));
        return "mascotas/vista/eliminar_mascota";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id) {
        mascotaService.eliminar(id);
        return "redirect:/mascotas/listado_mascotas";
    }
}