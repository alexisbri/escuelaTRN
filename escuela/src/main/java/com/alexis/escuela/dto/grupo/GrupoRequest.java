package com.alexis.escuela.dto.grupo;

import jakarta.validation.constraints.NotBlank;

public record GrupoRequest(

        @NotBlank(message = "El período es obligatorio")
        String periodo

) {
}
