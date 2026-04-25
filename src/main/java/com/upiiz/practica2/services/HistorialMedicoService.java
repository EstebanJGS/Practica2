package com.upiiz.practica2.services;

import java.util.List;

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
}