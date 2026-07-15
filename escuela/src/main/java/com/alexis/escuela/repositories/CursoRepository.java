package com.alexis.escuela.repositories;

import com.alexis.escuela.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marca la interfaz como un componente de acceso a datos (DAO).
// <Entidad, TipoDelID> → Obligatorio. Define qué entidad gestiona el repositorio y el tipo de su clave primaria.
public interface CursoRepository extends JpaRepository<Curso, Long> {



}
