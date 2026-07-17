package com.alexis.escuela.entities;

import com.alexis.escuela.enums.DiaSemana;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // Marca la clase como entidad JPA para mapear a tabla de BD.
@Builder // Permite construir objetos usando el patrón builder.
@NoArgsConstructor // Constructor vacío.
@AllArgsConstructor // Constructor con todos los campos (requerido por @Builder).
@Getter // Genera getters para todos los campos.
@Table(name = "HORARIOS") // Especifica el nombre exacto de la tabla en BD

public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HORARIO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Grupo grupo;

    @Enumerated(EnumType.STRING)
    @Column(name = "DIA", nullable = false)
    private DiaSemana diaSemana;

    @Column(name = "HORA_INICIO", nullable = false, length = 5)
    private String horaInicio;

    @Column(name = "HORA_FIN", nullable = false, length = 5)
    private String horaFin;



    public Horario asignarGrupo(Grupo grupo) {
        if (grupo == null)
            throw new IllegalArgumentException("El grupo es requerido");
        this.grupo = grupo;
        return this;
    }

    public void actualizar(String dia, String horaInicio, String horaFin) {
        validarDatos(dia, horaInicio, horaFin);
        this.diaSemana = DiaSemana.valueOf(dia);
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    private void validarDatos(String dia, String horaInicio, String horaFin) {
        if (dia == null || dia.isBlank())
            throw new IllegalArgumentException("El día es requerido");

        if (horaInicio == null || horaInicio.isBlank())
            throw new IllegalArgumentException("La hora de inicio es requerida");

        if (horaFin == null || horaFin.isBlank())
            throw new IllegalArgumentException("La hora de fin es requerida");

        if (!horaInicio.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$"))
            throw new IllegalArgumentException("Formato de hora inicio inválido (HH:MM)");

        if (!horaFin.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$"))
            throw new IllegalArgumentException("Formato de hora fin inválido (HH:MM)");

        if (horaInicio.compareTo(horaFin) >= 0)
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio");
    }




}
