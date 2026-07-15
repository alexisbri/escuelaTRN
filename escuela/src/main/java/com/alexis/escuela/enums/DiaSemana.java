package com.alexis.escuela.enums;

import com.alexis.escuela.exceptions.RecursoNoEncontradoException;
import com.alexis.escuela.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DiaSemana {

    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miércoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sábado");

    private final String desripcion;

    public static DiaSemana obtenerDiaSemanaPorDescripcion(String descripcion) {
        StringCustomUtils.validarNoVacio(descripcion, "La descripción es requerida");
        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion.trim());

        for (DiaSemana DiaSemana : values ()) {
            if (StringCustomUtils.quitarAcentos(DiaSemana.desripcion).equalsIgnoreCase(descripcionNormalizada))
                return DiaSemana;
        }

        throw new RecursoNoEncontradoException("No existe un día de la semana con la descripción: " + descripcion);
    }
}