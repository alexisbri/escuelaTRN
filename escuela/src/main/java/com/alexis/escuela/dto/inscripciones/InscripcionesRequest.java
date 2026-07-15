package com.alexis.escuela.dto.inscripciones;

import jakarta.validation.constraints.NotNull;

public record InscripcionesRequest(

        @NotNull(message = "El ID del alumno es obligatorio")
        Long idAlumno,

        @NotNull(message = "El ID del grupo es obligatorio")
        Long idGrupo

) {}
