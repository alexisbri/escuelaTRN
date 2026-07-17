package com.alexis.escuela.dto.aula;

import jakarta.validation.constraints.*;

public record AulaRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 2, max = 30, message = "El nombre es requerido y debe tener entre 2 y 30")
        String nombre,

        @NotNull(message = "La cantidad es requerida")
        @Positive(message = "La cantidad debe ser positiva")
        @Min(value = 1, message = "La capacidad minima es 1")
        @Max(value = 4, message = "Los capacidad máximo son 4")
        Integer capacidad

) {}
