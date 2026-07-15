package com.alexis.escuela.dto.maestros;

import com.alexis.escuela.dto.curso.DatosCurso;

import java.util.List;

public record MaestroResponse(

        Long id,
        String nombre,
        String email,
        String telefono,
        List<DatosCurso> cursos

) {}
