package com.upiiz.practica2.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upiiz.practica2.models.Usuario;
import com.upiiz.practica2.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Lógica para registrar un nuevo usuario
    public Usuario registrarUsuario(Usuario usuario) {
        Optional<Usuario> existeUsuario = usuarioRepository.findByEmail(usuario.getEmail());
        if (existeUsuario.isPresent()) {
            throw new RuntimeException("Error: El correo electrónico ya está registrado.");
        }
        // Se guarda la contraseña en texto plano según el requerimiento de la base de datos
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    // Lógica para autenticar (Login)
    public Usuario autenticar(String email, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        
        if (usuario.isPresent()) {
            // Comparamos contraseñas en texto plano
            if (usuario.get().getPassword().equals(password)) {
                return usuario.get();
            }
        }
        return null; // Retorna null si las credenciales son incorrectas
    }
}