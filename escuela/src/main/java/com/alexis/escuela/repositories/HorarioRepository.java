package com.alexis.escuela.repositories;

import com.alexis.escuela.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    boolean existsByGrupoIdAndDiaSemanaAndHoraInicioLessThanEqualAndHoraFinGreaterThanEqual(
            Long grupoId, String dia, String horaInicio, String horaFin
    );

    boolean existsByGrupoAulaIdAndDiaSemanaAndHoraInicioLessThanEqualAndHoraFinGreaterThanEqual(
            Long aulaId, String dia, String horaInicio, String horaFin
    );

    boolean existsByGrupoIdAndDiaSemanaAndHoraInicioLessThanEqualAndHoraFinGreaterThanEqualAndIdNot(
            Long grupoId, String dia, String horaInicio, String horaFin, Long id
    );

    boolean existsByGrupoAulaIdAndDiaSemanaAndHoraInicioLessThanEqualAndHoraFinGreaterThanEqualAndIdNot(
            Long aulaId, String dia, String horaInicio, String horaFin, Long id
    );

}
