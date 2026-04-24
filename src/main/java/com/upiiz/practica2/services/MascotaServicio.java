package com.upiiz.practica2.services;

import com.upiiz.practica2.models.Mascota;
import com.upiiz.practica2.repositories.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MascotaServicio {

    @Autowired
    private MascotaRepository mascotaRepository;

    public List<Mascota> listarTodas() {
        return mascotaRepository.findAll();
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
}