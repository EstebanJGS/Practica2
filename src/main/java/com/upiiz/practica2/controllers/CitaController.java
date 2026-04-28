package com.upiiz.practica2.controllers;

import java.util.List;

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

import com.upiiz.practica2.models.Cita;
import com.upiiz.practica2.services.CitaService;
import com.upiiz.practica2.services.MascotaServicio;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private MascotaServicio mascotaService;

    @GetMapping
    public String listar(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        List<Cita> citas = citaService.listarTodas();
        model.addAttribute("citas", citas);
        return "mascotas/vista/list-citas";
    }

    @GetMapping("/agregar")
    public String mostrarFormularioNuevaCita(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("cita", new Cita());
        model.addAttribute("mascotas", mascotaService.listarTodas());
        return "mascotas/vista/add-cita";
    }

    @PostMapping("/agregar")
    public String guardar(@Valid @ModelAttribute Cita cita, BindingResult result, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            model.addAttribute("mascotas", mascotaService.listarTodas());
            return "mascotas/vista/add-cita";
        }
        citaService.guardar(cita);
        return "redirect:/citas";
    }

    @GetMapping("/actualizar/{id}")
    public String mostrarActualizar(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        Cita cita = citaService.buscarPorId(id);
        if (cita == null) {
            throw new IllegalArgumentException("Cita no encontrada: " + id);
        }
        model.addAttribute("cita", cita);
        model.addAttribute("mascotas", mascotaService.listarTodas());
        return "mascotas/vista/actualizar-cita";
    }

    @PostMapping("/actualizar")
    public String actualizar(@Valid @ModelAttribute Cita cita, BindingResult result, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            model.addAttribute("mascotas", mascotaService.listarTodas());
            return "mascotas/vista/actualizar-cita";
        }
        citaService.guardar(cita);
        return "redirect:/citas";
    }

    @GetMapping("/eliminar/{id}")
    public String mostrarEliminar(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        Cita cita = citaService.buscarPorId(id);
        if (cita == null) {
            throw new IllegalArgumentException("Cita no encontrada: " + id);
        }
        model.addAttribute("cita", cita);
        return "mascotas/vista/delete-cita";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long citaId, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        citaService.eliminar(citaId);
        return "redirect:/citas";
    }

    @GetMapping("/estadistica")
    public String estadistica(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("totalMascotas", mascotaService.contarTodas());
        model.addAttribute("citasProgramadas", citaService.contarPorEstado(Cita.Estado.programada));
        model.addAttribute("citasCompletadas", citaService.contarPorEstado(Cita.Estado.completada));
        model.addAttribute("citasCanceladas",  citaService.contarPorEstado(Cita.Estado.cancelada));
        model.addAttribute("ultimasCitas", citaService.listarTodas());
        return "mascotas/vista/estadistica";
    }
}
