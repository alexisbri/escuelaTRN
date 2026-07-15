package com.alexis.escuela.dto.calificacion;

import java.time.LocalDate;

public record CalificacionResponse(

        Long id,
        Long idInscripcion,
        Double calificacion,
        LocalDate fechaRegistro

) {}
