package com.upiiz.practica2.models;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "historial_medico")
public class HistorialMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historial_id")
    private Long historialId;

    @NotNull(message = "Selecciona una mascota")
    @Column(name = "mascota_id", nullable = false)
    private Long mascotaId;

    @NotNull(message = "La fecha de consulta es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_consulta", nullable = false)
    private LocalDate fechaConsulta;

    @NotBlank(message = "El diagnóstico es obligatorio")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String diagnostico;

    @Column(columnDefinition = "TEXT")
    private String tratamiento;

    @NotBlank(message = "El nombre del veterinario es obligatorio")
    @Column(nullable = false, length = 100)
    private String veterinario;

    @Column(columnDefinition = "TEXT")
    private String notas;

    public Long getHistorialId() { return historialId; }
    public void setHistorialId(Long historialId) { this.historialId = historialId; }

    public Long getMascotaId() { return mascotaId; }
    public void setMascotaId(Long mascotaId) { this.mascotaId = mascotaId; }

    public LocalDate getFechaConsulta() { return fechaConsulta; }
    public void setFechaConsulta(LocalDate fechaConsulta) { this.fechaConsulta = fechaConsulta; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getVeterinario() { return veterinario; }
    public void setVeterinario(String veterinario) { this.veterinario = veterinario; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}
