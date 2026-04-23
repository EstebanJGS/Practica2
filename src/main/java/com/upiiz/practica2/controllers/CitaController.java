package com.upiiz.practica2.controllers;

import com.upiiz.practica2.model.Cita;
import com.upiiz.practica2.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaRepository citaRepository;

    // ── Página 9: Listado ──────────────────────────
    @GetMapping
    public String listar(Model model) {
        List<Cita> citas = citaRepository.findAll();
        model.addAttribute("citas", citas);
        return "mascotas/citas/list-citas";
    }

    // ── Página 10: Mostrar form agregar ────────────
    @GetMapping("/agregar")
    public String mostrarAgregar(Model model) {
        model.addAttribute("cita", new Cita());
        return "mascotas/citas/add-cita";
    }

    // ── Página 10: Procesar form agregar ───────────
    @PostMapping("/agregar")
    public String guardar(@ModelAttribute Cita cita) {
        citaRepository.save(cita);
        return "redirect:/citas";
    }

    // ── Página 11: Mostrar form actualizar ─────────
    @GetMapping("/actualizar/{id}")
    public String mostrarActualizar(@PathVariable Long id, Model model) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        model.addAttribute("cita", cita);
        return "mascotas/citas/update-cita";
    }

    // ── Página 11: Procesar form actualizar ────────
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Cita cita) {
        citaRepository.save(cita);
        return "redirect:/citas";
    }

    // ── Página 12: Mostrar confirmación eliminar ───
    @GetMapping("/eliminar/{id}")
    public String mostrarEliminar(@PathVariable Long id, Model model) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        model.addAttribute("cita", cita);
        return "mascotas/citas/delete-cita";
    }

    // ── Página 12: Procesar eliminación ───────────
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long citaId) {
        citaRepository.deleteById(citaId);
        return "redirect:/citas";
    }

    // ── Página 13: Estadísticas ───────────────────
    @GetMapping("/estadistica")
    public String estadistica(Model model) {
        model.addAttribute("totalMascotas", 150); // cámbialo por tu repo de mascotas
        model.addAttribute("citasProgramadas", citaRepository.countByEstado(Cita.Estado.programada));
        model.addAttribute("citasCompletadas", citaRepository.countByEstado(Cita.Estado.completada));
        model.addAttribute("citasCanceladas",  citaRepository.countByEstado(Cita.Estado.cancelada));
        model.addAttribute("ultimasCitas", citaRepository.findAll());
        return "mascotas/citas/estadistica";
    }
}