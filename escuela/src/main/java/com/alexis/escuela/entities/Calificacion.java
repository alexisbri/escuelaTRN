package com.alexis.escuela.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity // Marca la clase como entidad JPA para mapear a tabla de BD.
@Builder // Permite construir objetos usando el patrón builder.
@NoArgsConstructor // Constructor vacío.
@AllArgsConstructor // Constructor con todos los campos (requerido por @Builder).
@Getter // Genera getters para todos los campos.
@Table(name = "CALIFICACIONES") // Especifica el nombre exacto de la tabla en BD

public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CALIFICACION")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INSCRIPCION", nullable = false, unique = true)
    private Inscripcion inscripcion;

    @Column(name = "CALIFICACION", nullable = false)
    private BigDecimal calificacion;

    @Column(name = "FECHA_REGISTRO")
    private LocalDate fechaRegistro;


}
