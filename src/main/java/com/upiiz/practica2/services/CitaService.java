package com.upiiz.practica2.services;

import java.util.ArrayList;
import java.util.Collections;
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

    public long contarTodas() {
        return citaRepository.count();
    }

    public long contarPorEstado(Cita.Estado estado) {
        return citaRepository.countByEstado(estado);
    }

    public List<Long> contarPorMes() {
        List<Long> meses = new ArrayList<>(Collections.nCopies(12, 0L));
        for (Cita c : citaRepository.findAll()) {
            if (c.getFechaHora() != null) {
                int mes = c.getFechaHora().getMonthValue() - 1;
                meses.set(mes, meses.get(mes) + 1);
            }
        }
        return meses;
    }
}
