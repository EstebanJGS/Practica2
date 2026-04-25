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

import com.upiiz.practica2.models.HistorialMedico;
import com.upiiz.practica2.services.HistorialMedicoService;
import com.upiiz.practica2.services.MascotaServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/historial")
public class HistorialMedicoController {

    @Autowired
    private HistorialMedicoService historialService;

    @Autowired
    private MascotaServicio mascotaService;

    @GetMapping({"", "/"})
    public String listadoHistorial(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("historiales", historialService.listarTodos());
        return "mascotas/vista/listado_historial";
    }

    @GetMapping("/agregar_historial")
    public String agregarHistorial(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("historial", new HistorialMedico());
        model.addAttribute("mascotas", mascotaService.listarTodas()); // Para poder seleccionar a qué mascota pertenece
        return "mascotas/vista/agregar_historial";
    }

    @PostMapping("/guardar")
    public String guardarHistorial(@ModelAttribute HistorialMedico historial, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) {
            historialService.guardar(historial);
            return "redirect:/historial";
        }
        return "redirect:/login";
    }

    @GetMapping("/editar_historial/{id}")
    public String editarHistorial(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        HistorialMedico historial = historialService.buscarPorId(id);
        if (historial == null) {
            throw new IllegalArgumentException("ID de historial inválido:" + id);
        }
        model.addAttribute("historial", historial);
        model.addAttribute("mascotas", mascotaService.listarTodas());
        return "mascotas/vista/editar_historial";
    }

    @PostMapping("/actualizar")
    public String actualizarHistorial(@ModelAttribute HistorialMedico historial, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) {
            historialService.guardar(historial);
            return "redirect:/historial";
        }
        return "redirect:/login";
    }

    @GetMapping("/eliminar_historial/{id}")
    public String eliminarHistorialVista(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("historial", historialService.buscarPorId(id));
        return "mascotas/vista/eliminar_historial";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id) {
        historialService.eliminar(id);
        return "redirect:/historial";
    }
}