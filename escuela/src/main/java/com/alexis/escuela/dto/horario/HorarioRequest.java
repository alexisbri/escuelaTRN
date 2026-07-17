package com.alexis.escuela.dto.horario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record HorarioRequest(

        @NotNull(message = "El grupo es obligatorio")
        Long idGrupo,

        @NotBlank(message = "El día es obligatorio")
        @Pattern(regexp = "^(LUNES|MARTES|MIERCOLES|JUEVES|VIERNES|SABADO|DOMINGO)$",
                message = "El día debe ser un día de la semana válido")
        String dia,

        @NotBlank(message = "La hora de inicio es obligatoria")
        @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$",
                message = "La hora de inicio debe tener formato HH:MM")
        String horaInicio,

        @NotBlank(message = "La hora de fin es obligatoria")
        @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$",
                message = "La hora de fin debe tener formato HH:MM")
        String horaFin

) {
}
