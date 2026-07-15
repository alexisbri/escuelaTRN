package com.alexis.escuela.dto.calificacion;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DatosCalificacion(

        String curso,
        String periodo,
        BigDecimal calificacion

) {}
