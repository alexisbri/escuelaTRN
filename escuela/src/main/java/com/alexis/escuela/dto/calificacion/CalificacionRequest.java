package com.alexis.escuela.dto.calificacion;

import jakarta.validation.constraints.NotNull;

public record CalificacionRequest(

        @NotNull(message = "La calificación es obligatoria")
        Double calificacion

) {}
