package com.alexis.escuela.dto.grupo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GrupoRequest(

        @NotNull
        @Positive(message = "La cantidad debe ser positiva")
        Long cursoId,

        @NotNull
        @Positive(message = "La cantidad debe ser positiva")
        Long idMaestro,

        @NotNull
        @Positive(message = "La cantidad debe ser positiva")
        Long idAula,

        @NotBlank(message = "El período es obligatorio")
        String periodo

) {}
