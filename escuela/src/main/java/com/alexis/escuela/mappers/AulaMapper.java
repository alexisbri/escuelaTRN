package com.alexis.escuela.mappers;

import com.alexis.escuela.dto.aula.AulaRequest;
import com.alexis.escuela.dto.aula.AulaResponse;
import com.alexis.escuela.entities.Aula;
import org.springframework.stereotype.Component;

@Component
public class AulaMapper {

    public Aula requestAEntidad(AulaRequest request) {

        if (request == null) return null;

        return Aula.builder()
                .nombre(request.nombre())
                .capacidad(request.capacidad())
                .build();
    }

    public AulaResponse entidadAResponse(Aula aula) {

        if (aula == null) return null;

        return new AulaResponse(
                aula.getId(),
                aula.getNombre(),
                aula.getCapacidad()
        );
    }

}
