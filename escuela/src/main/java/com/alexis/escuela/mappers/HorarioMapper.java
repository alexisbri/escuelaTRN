package com.alexis.escuela.mappers;

import com.alexis.escuela.dto.horario.HorarioRequest;
import com.alexis.escuela.dto.horario.HorarioResponse;
import com.alexis.escuela.entities.Grupo;
import com.alexis.escuela.entities.Horario;
import com.alexis.escuela.enums.DiaSemana;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HorarioMapper implements CommonMapper<HorarioRequest, HorarioResponse, Horario>{

    @Override
    public Horario requestAEntidad(HorarioRequest request) {
        if (request == null) return null;

        return Horario.builder()
                .diaSemana(DiaSemana.valueOf(request.dia()))
                .horaInicio(request.horaInicio())
                .horaFin(request.horaFin())
                .build();
    }

    public Horario requestAEntidad(HorarioRequest request, Grupo grupo) {
        if (request == null) return null;

        Horario horario = requestAEntidad(request);

        return horario.asignarGrupo(grupo);
    }



    @Override
    public HorarioResponse entidadAResponse(Horario horario) {
        if (horario == null) return null;

        return new HorarioResponse(
                horario.getId(),
                horario.getGrupo().getId(),
                horario.getDiaSemana().name(),
                horario.getHoraInicio(),
                horario.getHoraFin()
        );
    }

}
