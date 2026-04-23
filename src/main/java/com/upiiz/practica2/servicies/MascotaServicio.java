package com.upiiz.practica2.services;

import com.upiiz.practica2.models.Mascota;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MascotaServicio {
    private List<Mascota> mascotas = new ArrayList<>();
    private Long idCounter = 1L;

    public List<Mascota> listarTodas() {
        return mascotas;
    }

    public void guardar(Mascota mascota) {
        if (mascota.getId() == null) {
            mascota.setId(idCounter++);
            mascotas.add(mascota);
        } else {
            for (int i = 0; i < mascotas.size(); i++) {
                if (mascotas.get(i).getId().equals(mascota.getId())) {
                    mascotas.set(i, mascota);
                    break;
                }
            }
        }
    }

    public Mascota buscarPorId(Long id) {
        return mascotas.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    public void eliminar(Long id) {
        mascotas.removeIf(m -> m.getId().equals(id));
    }
}