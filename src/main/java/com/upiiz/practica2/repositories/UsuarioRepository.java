package com.upiiz.practica2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upiiz.practica2.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    // Útil para verificar si un email ya existe en el registro y para el login
    Optional<Usuario> findByEmail(String email);
}