package com.upiiz.practica2.services;

import com.upiiz.practica2.models.Mascota;
import com.upiiz.practica2.repositories.MascotaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MascotaServicio {

    @Autowired
    private MascotaRepository mascotaRepository;

    public List<Mascota> listarTodas() {
        return mascotaRepository.findAll();
    }

    public long contarTodas() {
        return mascotaRepository.count();
    }

    public void guardar(Mascota mascota) {
        mascotaRepository.save(mascota);
    }

    public Mascota buscarPorId(Long id) {
        return mascotaRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        mascotaRepository.deleteById(id);
    }

    public Map<String, Long> contarPorEspecie() {
        Map<String, Long> result = new LinkedHashMap<>();
        for (Mascota m : mascotaRepository.findAll()) {
            String esp = (m.getEspecie() != null && !m.getEspecie().isBlank()) ? m.getEspecie() : "Otro";
            result.merge(esp, 1L, Long::sum);
        }
        return result;
    }
}