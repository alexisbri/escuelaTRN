package com.alexis.escuela.mappers;

import com.alexis.escuela.dto.alumno.AlumnoRequest;
import com.alexis.escuela.dto.alumno.AlumnoResponse;
import com.alexis.escuela.dto.calificacion.DatosCalificacion;
import com.alexis.escuela.entities.Alumno;
import com.alexis.escuela.utils.StringCustomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AlumnoMapper implements CommonMapper<AlumnoRequest, AlumnoResponse, Alumno> {

    @Override
    public Alumno requestAEntidad (AlumnoRequest request) {

        if (request == null) return null;

        return Alumno.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .fechaIngreso(LocalDate.now())
                .build();
    }


    public Alumno requestAEntidad (AlumnoRequest request, String email, String matricula) {

        if (request == null) return null;

        Alumno alumno = requestAEntidad(request);

        alumno.asignarDatosAcademicos(email, matricula);

        return alumno;
    }


    @Override
    public AlumnoResponse entidadAResponse (Alumno entidad) {

        if (entidad == null) return null;

        List<DatosCalificacion> calificacions = entidadADatosCalificacion(entidad);

        return new AlumnoResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getMatricula(),
                StringCustomUtils
                        .localDateAString(entidad.getFechaIngreso()),
                calificacions,
                entidad.calcularPromedio()
        );

    }


    private List<DatosCalificacion> entidadADatosCalificacion (Alumno entidad) {
        if (entidad == null || entidad.getInscripciones() == null || entidad.getInscripciones().isEmpty())
            return List.of();

        return entidad.getInscripciones().stream()
                .map(inscripcion -> new DatosCalificacion(
                        inscripcion.getGrupo().getCurso().getNombre(),
                        inscripcion.getGrupo().getPeriodo(),
                        inscripcion.getCalificacion() == null
                                ? inscripcion.getCalificacion().getCalificacion()
                                : null
                )).toList();
    }


}
