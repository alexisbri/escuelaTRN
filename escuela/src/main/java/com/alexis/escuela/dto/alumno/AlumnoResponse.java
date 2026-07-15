package com.alexis.escuela.dto.alumno;

import com.alexis.escuela.dto.calificacion.DatosCalificacion;
import com.alexis.escuela.dto.curso.DatosCurso;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record AlumnoResponse(

        Long id,
        String nombre,
        String email,
        String matricula,
        String fechaIngreso,
        List<DatosCalificacion> calificaciones,
        BigDecimal promedio


) {}
