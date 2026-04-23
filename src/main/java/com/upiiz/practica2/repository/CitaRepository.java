// src/main/java/com/upiiz/practica2/repository/CitaRepository.java
package com.upiiz.practica2.repository;

import com.upiiz.practica2.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    // Contar citas por estado (para estadísticas)
    long countByEstado(Cita.Estado estado);
}