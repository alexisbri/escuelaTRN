package com.alexis.escuela.services.aula;

import com.alexis.escuela.dto.aula.AulaRequest;
import com.alexis.escuela.dto.aula.AulaResponse;

import java.util.List;

public interface AulaService {

    List<AulaResponse> listar (String nombre, Integer capacidad);

    AulaResponse obtenerPorId (long id);

    AulaResponse registrar (AulaRequest request);

    AulaResponse actualizar (AulaRequest request, long id);

    void eliminar (long id);

}
