package com.alexis.escuela.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity // Marca la clase como entidad JPA para mapear a tabla de BD.
@Builder // Permite construir objetos usando el patrón builder.
@NoArgsConstructor // Constructor vacío.
@AllArgsConstructor // Constructor con todos los campos (requerido por @Builder).
@Getter // Genera getters para todos los campos.
@Table(name = "INSCRIPCIONES", uniqueConstraints = @UniqueConstraint(
        name = "INSCRIPCIONES_ALU_GRU_UK",
        columnNames = {"ID_ALUMNO", "ID_GRUPO"}
)) // Especifica el nombre exacto de la tabla en BD

public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INSCRIPCION")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ALUMNO", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Grupo grupo;

    @Builder.Default
    @Column(name = "FECHA_INSCRIPCION")
    private LocalDate fechaInscripcion = LocalDate.now();

    @OneToOne(mappedBy = "inscripcion")
    private Calificacion calificacion;

}
