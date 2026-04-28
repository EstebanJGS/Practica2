package com.upiiz.practica2.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upiiz.practica2.models.HistorialMedico;
import com.upiiz.practica2.repositories.HistorialMedicoRepository;

@Service
public class HistorialMedicoService {

    @Autowired
    private HistorialMedicoRepository historialRepository;

    public List<HistorialMedico> listarTodos() {
        return historialRepository.findAll();
    }

    public HistorialMedico guardar(HistorialMedico historial) {
        return historialRepository.save(historial);
    }

    public HistorialMedico buscarPorId(Long id) {
        return historialRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        historialRepository.deleteById(id);
    }

    public long contarTodos() {
        return historialRepository.count();
    }

    public List<Long> contarPorMes() {
        List<Long> meses = new ArrayList<>(Collections.nCopies(12, 0L));
        for (HistorialMedico h : historialRepository.findAll()) {
            if (h.getFechaConsulta() != null) {
                int mes = h.getFechaConsulta().getMonthValue() - 1;
                meses.set(mes, meses.get(mes) + 1);
            }
        }
        return meses;
    }

    public Map<String, Long> contarPorVeterinario() {
        Map<String, Long> raw = new LinkedHashMap<>();
        for (HistorialMedico h : historialRepository.findAll()) {
            String vet = (h.getVeterinario() != null && !h.getVeterinario().isBlank()) ? h.getVeterinario() : "Desconocido";
            raw.merge(vet, 1L, Long::sum);
        }
        return raw.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }
}