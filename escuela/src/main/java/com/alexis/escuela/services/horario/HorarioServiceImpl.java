package com.alexis.escuela.services.horario;

import com.alexis.escuela.dto.horario.HorarioRequest;
import com.alexis.escuela.dto.horario.HorarioResponse;
import com.alexis.escuela.entities.Grupo;
import com.alexis.escuela.entities.Horario;
import com.alexis.escuela.mappers.HorarioMapper;
import com.alexis.escuela.repositories.GrupoRepository;
import com.alexis.escuela.repositories.HorarioRepository;
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
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;
    private final GrupoRepository grupoRepository;
    private final HorarioMapper horarioMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HorarioResponse> listar() {
        log.info("Listando todos los horarios");
        return horarioRepository.findAll().stream()
                .map(horarioMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HorarioResponse obtenerPorId(Long id) {
        return horarioMapper.entidadAResponse(obtenerHorario(id));
    }

    @Override
    public HorarioResponse registrar(HorarioRequest request) {
        log.info("Registrando horario");

        Grupo grupo = ServiceUtils.obtenerEntidadException(
                grupoRepository, request.idGrupo(), Grupo.class);


        validarTraslapes(request, null);
        validarHorario(request);

        Horario horario = horarioMapper.requestAEntidad(request, grupo);
        horarioRepository.save(horario);

        log.info("Horario registrado con id {}", horario.getId());
        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {
        log.info("Actualizando horario con id {}", id);

        Horario horario = obtenerHorario(id);
        Grupo grupo = ServiceUtils.obtenerEntidadException(grupoRepository, request.idGrupo(), Grupo.class);

        validarTraslapes(request, id);
        validarHorario(request);

        horario.actualizar(request.dia(), request.horaInicio(), request.horaFin());
        horario.asignarGrupo(grupo);
        horarioRepository.save(horario);

        log.info("Horario con id {} actualizado", id);
        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public void eliminar(Long id) {
        Horario horario = obtenerHorario(id);
        log.info("Eliminando horario con id {}", id);
        horarioRepository.delete(horario);
        log.info("Horario con id {} eliminado", id);
    }

    // Métodos privados

    private Horario obtenerHorario(Long id) {
        return ServiceUtils.obtenerEntidadException(horarioRepository, id, Horario.class);
    }

    private void validarHorario(HorarioRequest request) {
        if (request.horaInicio().compareTo(request.horaFin()) >= 0) {
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio");
        }
    }

    private void validarTraslapes(HorarioRequest request, Long id) {
        log.info("Validando traslapes para grupo y aula");

        // Validar traslape por grupo
        boolean traslapeGrupo;
        if (id == null) {
            traslapeGrupo = horarioRepository.existsByGrupoIdAndDiaSemanaAndHoraInicioLessThanEqualAndHoraFinGreaterThanEqual(
                    request.idGrupo(),
                    request.dia(),
                    request.horaInicio(),
                    request.horaFin()
            );
        } else {
            traslapeGrupo = horarioRepository.existsByGrupoIdAndDiaSemanaAndHoraInicioLessThanEqualAndHoraFinGreaterThanEqualAndIdNot(
                    request.idGrupo(),
                    request.dia(),
                    request.horaInicio(),
                    request.horaFin(),
                    id
            );
        }

        if (traslapeGrupo) {
            throw new IllegalArgumentException("El horario se traslapa con otro horario del mismo grupo");
        }

        // Obtener aula del grupo para validar traslape por aula
        Grupo grupo = ServiceUtils.obtenerEntidadException(grupoRepository, request.idGrupo(), Grupo.class);
        Long aulaId = grupo.getAula().getId();

        boolean traslapeAula;
        if (id == null) {
            traslapeAula = horarioRepository.existsByGrupoAulaIdAndDiaSemanaAndHoraInicioLessThanEqualAndHoraFinGreaterThanEqual(
                    aulaId,
                    request.dia(),
                    request.horaInicio(),
                    request.horaFin()
            );
        } else {
            traslapeAula = horarioRepository.existsByGrupoAulaIdAndDiaSemanaAndHoraInicioLessThanEqualAndHoraFinGreaterThanEqualAndIdNot(
                    aulaId,
                    request.dia(),
                    request.horaInicio(),
                    request.horaFin(),
                    id
            );
        }

        if (traslapeAula) {
            throw new IllegalArgumentException("El horario se traslapa con otro horario en la misma aula");
        }
    }

}
