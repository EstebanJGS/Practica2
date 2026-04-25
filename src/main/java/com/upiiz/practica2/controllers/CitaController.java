package com.upiiz.practica2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.upiiz.practica2.models.Cita;
import com.upiiz.practica2.repositories.CitaRepository;
import com.upiiz.practica2.services.MascotaServicio;

@Controller
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private MascotaServicio mascotaService;

    @GetMapping
    public String listar(Model model) {
        List<Cita> citas = citaRepository.findAll();
        model.addAttribute("citas", citas);
        return "mascotas/vista/list-citas";
    }

    @GetMapping("/agregar") // O la ruta que estés usando para mostrar el formulario
    public String mostrarFormularioNuevaCita(Model model) {
        model.addAttribute("cita", new Cita());
        // ESTO ES VITAL. Inyectas la lista real de mascotas.
        model.addAttribute("mascotas", mascotaService.listarTodas()); 
        return "mascotas/vista/add-cita";
    }

    @PostMapping("/agregar")
    public String guardar(@ModelAttribute Cita cita) {
        citaRepository.save(cita);
        return "redirect:/citas";
    }

    @GetMapping("/actualizar/{id}")
    public String mostrarActualizar(@PathVariable Long id, Model model) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        model.addAttribute("cita", cita);
        return "mascotas/vista/actualizar-cita";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Cita cita) {
        citaRepository.save(cita);
        return "redirect:/citas";
    }

    @GetMapping("/eliminar/{id}")
    public String mostrarEliminar(@PathVariable Long id, Model model) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        model.addAttribute("cita", cita);
        return "mascotas/vista/delete-cita";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long citaId) {
        citaRepository.deleteById(citaId);
        return "redirect:/citas";
    }

    @GetMapping("/estadistica")
    public String estadistica(Model model) {
        model.addAttribute("totalMascotas", 150); // cámbialo por tu repo de mascotas
        model.addAttribute("citasProgramadas", citaRepository.countByEstado(Cita.Estado.programada));
        model.addAttribute("citasCompletadas", citaRepository.countByEstado(Cita.Estado.completada));
        model.addAttribute("citasCanceladas",  citaRepository.countByEstado(Cita.Estado.cancelada));
        model.addAttribute("ultimasCitas", citaRepository.findAll());
        return "mascotas/vista/estadistica";
    }
}