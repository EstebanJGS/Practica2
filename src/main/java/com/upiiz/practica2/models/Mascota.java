package com.upiiz.practica2.models;
 
import java.time.LocalDate;
 
import org.springframework.format.annotation.DateTimeFormat;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "mascotas")
public class Mascota {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mascota_id")
    private Long id;
 
    @Column(name = "usuario_id")
    private Integer usuarioId;
 
    @Column(nullable = false, length = 100)
    private String nombre;
 
    @Column(nullable = false)
    private String especie;
 
    @Column(name = "fecha_nacimiento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;  // era String, JPA no puede mapear String a DATE
 
    public Mascota() {}
 
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
 
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
 
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
 
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
 
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}
 