package com.alexis.escuela.services.grupo;

import com.alexis.escuela.dto.grupo.GrupoRequest;
import com.alexis.escuela.dto.grupo.GrupoResponse;
import com.alexis.escuela.entities.Aula;
import com.alexis.escuela.entities.Curso;
import com.alexis.escuela.entities.Grupo;
import com.alexis.escuela.entities.Maestro;
import com.alexis.escuela.exceptions.EntidadRelacionadaException;
import com.alexis.escuela.mappers.GrupoMapper;
import com.alexis.escuela.repositories.AulaRepository;
import com.alexis.escuela.repositories.CursoRepository;
import com.alexis.escuela.repositories.GrupoRepository;
import com.alexis.escuela.repositories.MaestroRepository;
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
public class GrupoServiceImpl implements GrupoService {

    private final CursoRepository cursoRepository;
    private final GrupoRepository grupoRepository;
    private final GrupoMapper grupoMapper;
    private final MaestroRepository maestroRepository;
    private final AulaRepository aulaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GrupoResponse> listar () {

        log.info("Listando todos los grupos.");

        return grupoRepository.findAll().stream()
                .map(grupoMapper::entidadAResponse).toList();

    }


    @Override
    @Transactional(readOnly = true)
    public GrupoResponse obtenerPorId (Long id) {

        return grupoMapper.entidadAResponse(obtenerGrupo(id));
    }


    @Override
    public GrupoResponse registrar (GrupoRequest request) {

        log.info("Registrando grupo.");

        validarDatosUnicos(request);

        Curso curso = ServiceUtils
                .obtenerEntidadException(cursoRepository,request.cursoId(), Curso.class);

        Maestro maestro = ServiceUtils
                .obtenerEntidadException(maestroRepository,request.idMaestro(), Maestro.class);

        Aula aula = ServiceUtils
                .obtenerEntidadException(aulaRepository,request.idAula(), Aula.class);

        if (grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(
                request.cursoId(),
                request.idMaestro(),
                request.idAula(),
                request.periodo()))

            throw new IllegalArgumentException("Ya se encuentra un grupo duplicado.");

        Grupo grupo = grupoMapper.requestAEntidad(request, curso, maestro, aula);
        grupoRepository.save(grupo);

        return grupoMapper.entidadAResponse(grupo);
    }


    public GrupoResponse actualizar (GrupoRequest request, Long id) {

        Grupo grupo = obtenerGrupo(id);
        validarDatosUnicosActualizar(request, id);

        Curso curso = ServiceUtils
                .obtenerEntidadException(cursoRepository,request.cursoId(), Curso.class);

        Maestro maestro = ServiceUtils
                .obtenerEntidadException(maestroRepository,request.idMaestro(), Maestro.class);

        Aula aula = ServiceUtils
                .obtenerEntidadException(aulaRepository,request.idAula(), Aula.class);

        if (grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(
                request.cursoId(),
                request.idMaestro(),
                request.idAula(),
                request.periodo(),
                id
        )) {
            throw new IllegalArgumentException("Ya existe un grupo con los mismos datos");
        }

        log.info("Actualizando grupo con id {}", id);

        grupo.actualizar(
                curso,
                maestro,
                aula,
                request.periodo()
        );

        grupoRepository.save(grupo);

        log.info("Grupo con id {} actualizado.", id);

        return grupoMapper.entidadAResponse(grupo);

    }


    @Override
    public void eliminar (Long id) {

        Grupo grupo = obtenerGrupo(id);

        log.info("Eliminando grupo con id {}", id);

        if (!grupo.getInscripcions().isEmpty())
            throw new EntidadRelacionadaException("No se puede eliminar el grupo porque tiene inscripciones asociadas.");

        if (!grupo.getHorarios().isEmpty())
            throw new EntidadRelacionadaException("No se puede eliminar el grupo porque tiene horarios asociados.");

        grupoRepository.delete(grupo);

        log.info("Grupo con id {} eliminado.", id);
    }



    /// METODOS PRIVADOS ---



    private Grupo obtenerGrupo(Long id) {
        return ServiceUtils.obtenerEntidadException(grupoRepository, id, Grupo.class);
    }


    private void validarDatosUnicos(GrupoRequest request) {

        log.info("Validando grupo único.");

        if (grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(request.cursoId(), request.idMaestro(), request.idAula(), request.periodo()))
            throw new IllegalArgumentException("Ya se encuentra un grupo con mismas caracteristicas: " + request);

    }


    private void validarDatosUnicosActualizar(GrupoRequest request, Long id) {

        log.info("Validando grupo único.");

        if (grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(request.cursoId(), request.idMaestro(), request.idAula(), request.periodo(), id))
            throw new IllegalArgumentException("Ya se encuentra un grupo con mismas caracteristicas: " + request);

    }

}
