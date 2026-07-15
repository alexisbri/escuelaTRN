package com.alexis.escuela.services.alumno;

import com.alexis.escuela.dto.alumno.AlumnoRequest;
import com.alexis.escuela.dto.alumno.AlumnoResponse;
import com.alexis.escuela.entities.Alumno;
import com.alexis.escuela.exceptions.EntidadRelacionadaException;
import com.alexis.escuela.mappers.AlumnoMapper;
import com.alexis.escuela.repositories.AlumnoRepository;
import com.alexis.escuela.repositories.InscripcionRepository;
import com.alexis.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;

    private final InscripcionRepository inscripcionRepository;

    private final AlumnoMapper alumnoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponse> listar() {

        log.info("Listando todos los alumno.");

        return alumnoRepository.findAll().stream()
                .map(alumnoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoResponse obtenerPorId(Long id) {

        log.info("Buscando por ID.");

        return alumnoMapper.entidadAResponse(obtenerAlumno(id));
    }

    @Override
    public AlumnoResponse registrar(AlumnoRequest request) {

        log.info("Registrando nuevo alumno.");

        Alumno alumno = alumnoMapper.requestAEntidad(request,
                generarEmail(request),
                generarMatricula(request));

        alumnoRepository.save(alumno);

        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public AlumnoResponse actualizar(AlumnoRequest request, Long id) {

        Alumno alumno = obtenerAlumno(id);

        log.info("Actualizando alumno con id {}.", id);

        if (alumno.cambioEnDatos(request.nombre().trim(), request.apellidoPaterno().trim(),
                request.apellidoPaterno().trim())) {

            alumno.actualizar(
                    request.nombre(),
                    request.apellidoPaterno(),
                    request.apellidoMaterno(),
                    generarEmail(request),
                    generarMatricula(request)
            );

            log.info("Datos académicos regenerados para el alumno con id: {}", id);

        }

        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public void eliminar(Long id) {

        Alumno alumno = obtenerAlumno(id);

        log.info("Eliminar alumno con id: {}", id);

        if (inscripcionRepository.existsByAlumnoId(id))
            throw new EntidadRelacionadaException(
                    "No se puede eliminar al alumno ya que tiene inscripciones asignadas");

        alumnoRepository.delete(alumno);

        log.info("Alumno con id {} eliminado", id);

    }


    private Alumno obtenerAlumno (Long id) {
        return ServiceUtils.obtenerEntidadException(alumnoRepository, id, Alumno.class);
    }


    private String generarEmail (AlumnoRequest request) {
        log.info("Generando email.");
        return alumnoRepository.generarEmail(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim());
    }

    private String generarMatricula (AlumnoRequest request) {
        log.info("Generando matricula.");
        return alumnoRepository.generarMatricula(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim());
    }

}
