package com.upiiz.practica2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upiiz.practica2.models.Cita;
import com.upiiz.practica2.repositories.CitaRepository;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public List<Cita> listarTodas() {
        return citaRepository.findAll();
    }

    public Cita guardar(Cita cita) {
        return citaRepository.save(cita);
    }

    public Cita buscarPorId(Long id) {
        return citaRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }

    public long contarPorEstado(Cita.Estado estado) {
        return citaRepository.countByEstado(estado);
    }
}
