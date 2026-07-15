package com.alexis.escuela.services.aula;

import com.alexis.escuela.dto.aula.AulaRequest;
import com.alexis.escuela.dto.aula.AulaResponse;
import com.alexis.escuela.entities.Aula;
import com.alexis.escuela.exceptions.RecursoNoEncontradoException;
import com.alexis.escuela.mappers.AulaMapper;
import com.alexis.escuela.repositories.AulaRepository;
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
    private final AulaMapper aulaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AulaResponse> listar (String nombre, Integer capacidad) {

        log.info("Listando aulas...");

        return aulaRepository.findAll().stream()
                .map(aulaMapper::aulaResponse).toList();
    }


    @Override
    @Transactional(readOnly = true)
    public AulaResponse obtenerPorId (long id) {

        return aulaMapper.aulaResponse(obtenerAulaOException(id));
    }


    @Override
    public AulaResponse registrar (AulaRequest request) {

        log.info("Registrando aula...");

        Aula aula = aulaMapper.requestAula(request);
        aulaRepository.save(aula);

        log.info("Nueva aula {} registrada", aula.getNombre());

        return aulaMapper.aulaResponse(aula);
    }


    public AulaResponse actualizar (AulaRequest request, long id) {

        Aula aula = obtenerAulaOException(id);

        log.info("Actualizando aula con id {}", id);

        aula.actualizar(
                request.nombre(),
                request.capacidad()
        );

        log.info("Aula con id {} actualizada.", id);

        return aulaMapper.aulaResponse(aula);

    }


    @Override
    public void eliminar (long id) {

        Aula aula = obtenerAulaOException(id);

        aulaRepository.delete(aula);

        log.info("Aula con id {} eliminada.", id);

    }


    private Aula obtenerAulaOException(Long id) {

        log.info("Buscando aula por id: {}", id);

        return aulaRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Aula no encontrado con id: " + id));
    }

}
