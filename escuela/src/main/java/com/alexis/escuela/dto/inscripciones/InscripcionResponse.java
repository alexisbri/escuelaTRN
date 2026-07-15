package com.alexis.escuela.dto.inscripciones;

import java.time.LocalDate;

public record InscripcionResponse(

        Long id,
        Long idAlumno,
        Long idGrupo,
        LocalDate fechaInscripcion

) {}
