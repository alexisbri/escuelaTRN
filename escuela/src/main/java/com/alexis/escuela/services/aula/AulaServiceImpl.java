package com.alexis.escuela.services.aula;

import com.alexis.escuela.dto.aula.AulaRequest;
import com.alexis.escuela.dto.aula.AulaResponse;
import com.alexis.escuela.entities.Aula;
import com.alexis.escuela.exceptions.EntidadRelacionadaException;
import com.alexis.escuela.mappers.AulaMapper;
import com.alexis.escuela.repositories.AulaRepository;
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
public class AulaServiceImpl implements AulaService {

    private final AulaRepository aulaRepository;
    private final GrupoRepository grupoRepository;
    private final AulaMapper aulaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AulaResponse> listar() {

        log.info("Listando todas las aulas.");

        return aulaRepository.findAll().stream()
                .map(aulaMapper::entidadAResponse).toList();

    }


    @Override
    @Transactional(readOnly = true)
    public AulaResponse obtenerPorId (Long id) {

        return aulaMapper.entidadAResponse(obtenerAula(id));
    }



    @Override
    public AulaResponse registrar (AulaRequest request) {

        log.info("Registrando aula.");

        validarDatosUnicos(request);

        Aula aula = aulaMapper.requestAEntidad(request);
        aulaRepository.save(aula);

        return aulaMapper.entidadAResponse(aula);
    }


    public AulaResponse actualizar (AulaRequest request, Long id) {

        Aula aula = obtenerAula(id);
        validarDatosUnicosActualizar(request, id);

        log.info("Actualizando aula con id {}", id);

        aula.actualizar(
                request.nombre(),
                request.capacidad()
        );

        log.info("Aula con id {} actualizada.", id);

        return aulaMapper.entidadAResponse(aula);

    }


    @Override
    public void eliminar (Long id) {

        Aula aula = obtenerAula(id);

        log.info("Eliminando aula.");

        if (grupoRepository.existsByAulaId(id))
            throw new EntidadRelacionadaException("No se puede eliminar el aula ya que tiene grupos asignados");

        aulaRepository.delete(aula);

        log.info("Aula con id {} eliminada.", id);

    }



    /// METODOS PRIVADOS -----



    private Aula obtenerAula(Long id){
        return ServiceUtils.obtenerEntidadException(aulaRepository, id, Aula.class);
    }


    private void validarDatosUnicos(AulaRequest request) {

        log.info("Validando nombre único.");

        if (aulaRepository.existsByNombreIgnoreCase(request.nombre()))
            throw new IllegalArgumentException("Ya se encuentra registrado el nombre: " + request.nombre());

    }


    private void validarDatosUnicosActualizar(AulaRequest request, Long id) {

        log.info("Validando cambio en nombre único.");

        if (aulaRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre(), id))
            throw new IllegalArgumentException("Ya se encuentra un aula registrada con el nombre: " + request.nombre());

    }

}
