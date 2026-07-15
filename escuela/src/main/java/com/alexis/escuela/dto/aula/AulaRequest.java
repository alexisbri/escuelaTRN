package com.alexis.escuela.dto.aula;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AulaRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 2, max = 30, message = "El nombre es requerido y debe tener entre 2 y 30")
        String nombre,

        @NotNull(message = "La cantidad es requerida")
        @Positive(message = "La cantidad debe ser positiva")
        Integer capacidad

) {}
