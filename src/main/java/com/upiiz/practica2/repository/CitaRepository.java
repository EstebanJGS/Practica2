// src/main/java/com/upiiz/practica2/repository/CitaRepository.java
package com.upiiz.practica2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upiiz.practica2.model.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    //Contar citas para las shit estadísticas
    long countByEstado(Cita.Estado estado);
}