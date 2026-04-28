package com.upiiz.practica2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.upiiz.practica2.models.Mascota;
import com.upiiz.practica2.models.Usuario;
import com.upiiz.practica2.services.MascotaServicio;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mascotas")
public class MascotasController {

    @Autowired
    private MascotaServicio mascotaService;

    @GetMapping({"", "/"})
    public String baseRedirect() {
        return "redirect:/mascotas/index";
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("totalMascotas", mascotaService.contarTodas());
        return "mascotas/index";
    }

    @GetMapping("/listado_mascotas")
    public String listadoMascotas(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("mascotas", mascotaService.listarTodas());
        return "mascotas/vista/listado_mascotas";
    }

    @GetMapping("/agregar_mascota")
    public String agregarMascota(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("mascota", new Mascota());
        return "mascotas/vista/agregar_mascota";
    }

    @PostMapping("/guardar")
    public String guardarMascota(@Valid @ModelAttribute Mascota mascota, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "mascotas/vista/agregar_mascota";
        }
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario != null) {
            mascota.setUsuarioId(usuario.getUsuarioId());
            mascotaService.guardar(mascota);
            return "redirect:/mascotas/listado_mascotas";
        }
        return "redirect:/login";
    }

    @GetMapping("/editar_mascota/{id}")
    public String editarMascota(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        Mascota mascota = mascotaService.buscarPorId(id);
        if (mascota == null) {
            throw new IllegalArgumentException("ID de mascota inválido:" + id);
        }
        model.addAttribute("mascota", mascota);
        return "mascotas/vista/editar_mascota";
    }

    @PostMapping("/actualizar")
    public String actualizarMascota(@Valid @ModelAttribute Mascota mascota, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "mascotas/vista/editar_mascota";
        }
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario != null) {
            mascota.setUsuarioId(usuario.getUsuarioId());
            mascotaService.guardar(mascota);
            return "redirect:/mascotas/listado_mascotas";
        }
        return "redirect:/login";
    }

    @GetMapping("/eliminar_mascota/{id}")
    public String eliminarMascota(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("mascota", mascotaService.buscarPorId(id));
        return "mascotas/vista/eliminar_mascota";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        mascotaService.eliminar(id);
        return "redirect:/mascotas/listado_mascotas";
    }
}
