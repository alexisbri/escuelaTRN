package com.alexis.escuela.services.curso;

import com.alexis.escuela.dto.aula.AulaRequest;
import com.alexis.escuela.dto.aula.AulaResponse;
import com.alexis.escuela.dto.curso.CursoRequest;
import com.alexis.escuela.dto.curso.CursoResponse;
import com.alexis.escuela.entities.Aula;
import com.alexis.escuela.entities.Curso;
import com.alexis.escuela.exceptions.EntidadRelacionadaException;
import com.alexis.escuela.exceptions.RecursoNoEncontradoException;
import com.alexis.escuela.mappers.CursoMapper;
import com.alexis.escuela.repositories.CursoRepository;
import com.alexis.escuela.repositories.GrupoRepository;
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
public class CursoServiceImpl implements CursoService{

    private final CursoRepository cursoRepository;
    private final GrupoRepository grupoRepository;
    private final CursoMapper cursoMapper;


    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listar () {

        log.info("Listando todos los cursos.");

        return cursoRepository.findAll().stream()
                .map(cursoMapper::entidadAResponse).toList();

    }


    @Override
    @Transactional(readOnly = true)
    public CursoResponse obtenerPorId (Long id) {

        return cursoMapper.entidadAResponse(obtenerCurso(id));
    }


    @Override
    public CursoResponse registrar (CursoRequest request) {

        log.info("Registrando curso.");

        validarDatosUnicos(request);

        Curso curso = cursoMapper.requestAEntidad(request);
        cursoRepository.save(curso);

        return cursoMapper.entidadAResponse(curso);
    }


    public CursoResponse actualizar (CursoRequest request, Long id) {

        Curso curso = obtenerCurso(id);
        validarDatosUnicosActualizar(request, id);

        log.info("Actualizando curso con id {}", id);

        curso.actualizar(
                request.nombre(),
                request.descripcion(),
                request.creditos()
        );

        log.info("Curso con id {} actualizada.", id);

        return cursoMapper.entidadAResponse(curso);

    }


    @Override
    public void eliminar (Long id) {

        Curso curso = obtenerCurso(id);

        log.info("Eliminando curso.");

        if (grupoRepository.existsByCursoId(id))
            throw new EntidadRelacionadaException("No se puede eliminar el curso ya que tiene grupos asignados");

        cursoRepository.delete(curso);

        log.info("Curso con id {} eliminada.", id);

    }



    /// METODOS PRIVADOS ---



    private Curso obtenerCurso(Long id) {
        return ServiceUtils.obtenerEntidadException(cursoRepository, id, Curso.class);
    }


    private void validarDatosUnicos(CursoRequest request) {

        log.info("Validando nombre único.");

        if (cursoRepository.existsByNombreIgnoreCase(request.nombre()))
            throw new IllegalArgumentException("Ya se encuentra registrado el nombre: " + request.nombre());

    }


    private void validarDatosUnicosActualizar(CursoRequest request, Long id) {

        log.info("Validando cambio en nombre único.");

        if (cursoRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre(), id))
            throw new IllegalArgumentException("Ya se encuentra registrado el nombre: " + request.nombre());

    }

}
