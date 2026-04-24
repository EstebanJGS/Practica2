package com.upiiz.practica2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upiiz.practica2.models.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}