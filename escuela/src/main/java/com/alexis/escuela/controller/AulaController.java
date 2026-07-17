package com.alexis.escuela.controller;

import com.alexis.escuela.dto.aula.AulaRequest;
import com.alexis.escuela.dto.aula.AulaResponse;
import com.alexis.escuela.services.aula.AulaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aula")
public class AulaController extends CommonController<AulaRequest, AulaResponse, AulaService> {

    public AulaController (AulaService service) {
        super(service);
    }

}
