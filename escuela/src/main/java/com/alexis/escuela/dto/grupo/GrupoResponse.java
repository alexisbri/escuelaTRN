package com.alexis.escuela.dto.grupo;

public record GrupoResponse(

        Long id,
        Long idCurso,
        Long idMaestro,
        Long idAula,
        String periodo

) {
}
