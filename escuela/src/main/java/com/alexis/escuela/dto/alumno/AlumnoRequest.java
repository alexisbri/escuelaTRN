package com.alexis.escuela.dto.alumno;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AlumnoRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 4, max = 50, message = "El nombre no debe exceder los 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellido paterno es obligatorio")
        @Size(min = 4, max = 50, message = "El apellido paterno no debe exceder los 50 caracteres")
        String apellidoPaterno,

        @NotBlank(message = "El apellido materno es obligatorio")
        @Size(min = 4, max = 50, message = "El apellido materno no debe exceder los 50 caracteres")
        String apellidoMaterno
/*
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe tener un formato válido")
        @Size(min = 4, max = 100, message = "El email no debe exceder los 100 caracteres")
        String email


        @NotBlank(message = "La matrícula es obligatoria")
        String matricula,



        @NotNull(message = "La fecha de ingreso es obligatoria")
        @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
        LocalDate fechaIngreso


 */


) {}
