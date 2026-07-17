package com.alexis.escuela.repositories;

import com.alexis.escuela.entities.Aula;
import com.alexis.escuela.entities.Curso;
import com.alexis.escuela.entities.Grupo;
import com.alexis.escuela.entities.Maestro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    // SELECT COUNT(*) FROM GRUPOS WHERE ID_MAESTRO = ?;
    boolean existsByMaestroId(Long idMaestro);

    // SELECT COUNT(*) FROM GRUPOS WHERE ID_AULA = ?;
    boolean existsByAulaId(Long idAula);

    // SELECT COUNT(*) FROM CURSO WHERE ID_CURSO = ?;
    boolean existsByCursoId(Long idCurso);

    boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(
            Long cursoId, Long maestroId, Long aulaId, String periodo
    );

    boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(
            Long cursoId, Long maestroId, Long aulaId, String periodo, Long id
    );

}
