package com.alexis.escuela.dto.horario;

public record HorarioResponse(

        Long id,
        Long idGrupo,
        String dia,
        String horaInicio,
        String horaFin

) {}
