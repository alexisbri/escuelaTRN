package com.alexis.escuela.services.curso;

import com.alexis.escuela.dto.curso.CursoRequest;
import com.alexis.escuela.dto.curso.CursoResponse;

import java.util.List;

public interface CursoService {

    List<CursoResponse> listar (String nombre, Integer capacidad);

    CursoResponse obtenerPorId (long id);

    CursoResponse registrar (CursoRequest request);

    CursoResponse actualizar (CursoRequest request, long id);

    void eliminar (long id);

}
