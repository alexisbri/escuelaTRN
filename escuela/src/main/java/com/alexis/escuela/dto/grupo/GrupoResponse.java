package com.alexis.escuela.dto.grupo;

import com.alexis.escuela.dto.datos.DatosAula;
import com.alexis.escuela.dto.datos.DatosCurso;
import com.alexis.escuela.dto.datos.DatosMaestro;

import java.util.List;

public record GrupoResponse(

        Long id,
        DatosCurso curso,
        DatosMaestro maestro,
        DatosAula aula,
        List<String> horarios,
        String periodo

) {}
