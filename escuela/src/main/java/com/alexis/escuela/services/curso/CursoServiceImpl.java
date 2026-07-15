package com.alexis.escuela.services.curso;

import com.alexis.escuela.dto.curso.CursoRequest;
import com.alexis.escuela.dto.curso.CursoResponse;
import com.alexis.escuela.entities.Curso;
import com.alexis.escuela.exceptions.RecursoNoEncontradoException;
import com.alexis.escuela.mappers.CursoMapper;
import com.alexis.escuela.repositories.CursoRepository;
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
    private final CursoMapper cursoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listar (String nombre, Integer capacidad) {
        log.info("Listando cursos...");
        return cursoRepository.findAll().stream()
                .map(cursoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CursoResponse obtenerPorId(long id) {
        return cursoMapper.entidadAResponse(obtenerCursoOException(id));
    }

    @Override
    public CursoResponse registrar(CursoRequest request) {
        log.info("Registrando curso...");
        Curso curso = cursoMapper.requestAEntidad(request);
        cursoRepository.save(curso);
        log.info("Nuevo curso {} registrado", curso.getNombre());
        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public CursoResponse actualizar(CursoRequest request, long id) {
        Curso curso = obtenerCursoOException(id);
        log.info("Actualizando curso con id {}", id);
        curso.actualizar(
                request.nombre(),
                request.descripcion(),
                request.creditos()
        );
        log.info("Curso con id {} actualizado.", id);
        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public void eliminar(long id) {
        Curso curso = obtenerCursoOException(id);
        cursoRepository.delete(curso);
        log.info("Curso con id {} eliminado.", id);
    }

    private Curso obtenerCursoOException(Long id) {
        log.info("Buscando curso por id: {}", id);
        return cursoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Curso no encontrado con id: " + id));
    }

}
