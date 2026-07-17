package com.alexis.escuela.dto.curso;

import jakarta.validation.constraints.*;

public record CursoRequest(

        @NotBlank(message = "El nombre es requerido.")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100.")
        String nombre,

        @Size(min = 5, max = 200, message = "La descripción debe tener entre 5 y 200 caracteres.")
        String descripcion,

        @NotNull(message = "Los creditos son requeridos.")
        @Min(value = 1, message = "Los créditos mínimos son 1.")
        @Max(value = 10, message = "Los créditos máximo son 10.")
        Integer creditos

) {}
