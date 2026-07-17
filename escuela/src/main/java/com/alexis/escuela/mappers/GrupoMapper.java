package com.alexis.escuela.mappers;

import com.alexis.escuela.dto.datos.DatosAula;
import com.alexis.escuela.dto.datos.DatosCurso;
import com.alexis.escuela.dto.datos.DatosMaestro;
import com.alexis.escuela.dto.grupo.GrupoRequest;
import com.alexis.escuela.dto.grupo.GrupoResponse;
import com.alexis.escuela.entities.Aula;
import com.alexis.escuela.entities.Curso;
import com.alexis.escuela.entities.Grupo;
import com.alexis.escuela.entities.Maestro;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GrupoMapper implements CommonMapper<GrupoRequest, GrupoResponse, Grupo>{

    @Override
    public Grupo requestAEntidad(GrupoRequest request) { // Recibe periodo

        if (request == null) return null;

        return Grupo.builder()
                .periodo(request.periodo().trim())
                .build();

    }

    // Recibe Entidades
    public Grupo requestAEntidad(GrupoRequest request, Curso curso, Maestro maestro, Aula aula) {

        if (request == null) return null;

        Grupo grupo = requestAEntidad(request);

        return grupo.asignarDatos(curso, maestro, aula);

    }


    @Override
    public GrupoResponse entidadAResponse(Grupo grupo) {

        if (grupo == null) return null;

        return new GrupoResponse(
                grupo.getId(),
                getDatosCurso(grupo),
                getDatosMaestro(grupo),
                getDatosAula(grupo),
                getHorarios(grupo),
                grupo.getPeriodo()
        );
    }



    /// Metodos privados



    private DatosCurso getDatosCurso (Grupo grupo) {

        if (grupo == null) return null;

        return new DatosCurso(
                grupo.getCurso().getNombre(),
                grupo.getCurso().getDescripcion(),
                grupo.getCurso().getCreditos());

    }



    private DatosMaestro getDatosMaestro (Grupo grupo) {

        if (grupo == null) return null;

        return new DatosMaestro(
                String.join(" ",
                        grupo.getMaestro().getNombre(),
                        grupo.getMaestro().getApellidoPaterno(),
                        grupo.getMaestro().getApellidoMaterno()),
                grupo.getMaestro().getEmail(),
                grupo.getMaestro().getTelefono());

    }



    private DatosAula getDatosAula (Grupo grupo) {

        if (grupo == null) return null;

        return new DatosAula(
                grupo.getAula().getNombre(),
                grupo.getAula().getCapacidad());

    }



    private List<String> getHorarios (Grupo grupo) {

        if (grupo == null) return List.of();

        return grupo.getHorarios().stream()
                .map(horario -> "%s %s - %s".formatted(
                       horario.getDiaSemana(),
                       horario.getHoraInicio(),
                       horario.getHoraFin()
                )).toList();

    }

}
