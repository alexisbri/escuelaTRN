package com.alexis.escuela.entities;

import com.alexis.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity // Marca la clase como entidad JPA para mapear a tabla de BD.
@Builder // Permite construir objetos usando el patrón builder.
@NoArgsConstructor // Constructor vacío.
@AllArgsConstructor // Constructor con todos los campos (requerido por @Builder).
@Getter // Genera getters para todos los campos.
@Table(name = "GRUPOS", uniqueConstraints = @UniqueConstraint(
        name = "GRUPO_CU_MA_AU_PE_UK",
        columnNames = {"ID_CURSO", "ID_MAESTRO", "ID_AULA", "PERIODO"}
)) // Especifica el nombre exacto de la tabla en BD

public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GRUPO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MAESTRO", nullable = false)
    private Maestro maestro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AULA", nullable = false)
    private Aula aula;

    @JoinColumn(name = "PERIODO", nullable = false)
    private String periodo;

    @Builder.Default
    @OneToMany(mappedBy = "grupo")
    private List<Horario> horarios = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "grupo")
    private List<Inscripcion> inscripcions = new ArrayList<>();


    // Asignamos las entidades a las variables solicitantes
    public Grupo asignarDatos(Curso curso, Maestro maestro, Aula aula) {

        if (curso == null)
            throw new IllegalArgumentException("El curso es requerido.");

        if (maestro == null)
            throw new IllegalArgumentException("El maestro es requerido.");

        if (aula == null)
            throw new IllegalArgumentException("El aula es requerida.");

        this.curso = curso;
        this.maestro = maestro;
        this.aula = aula;

        return this;

    }

    public void actualizar(Curso curso, Maestro maestro, Aula aula, String periodo) {

        validarDatos(curso, maestro, aula, periodo);

        this.curso = curso;
        this.maestro = maestro;
        this.aula = aula;
        this.periodo = periodo.trim();

    }

    private void validarDatos(Curso curso, Maestro maestro, Aula aula, String periodo) {

        if (curso == null)
            throw new IllegalArgumentException("Curso es requerido");

        if (maestro == null)
            throw new IllegalArgumentException("Maestro es requerido");

        if (aula == null)
            throw new IllegalArgumentException("Aula es requerida");

        StringCustomUtils.validarTamanio(periodo, 4, 7,
                "El periodo es requerido y debe tener entre 4 y 100 caracteres");

    }

}
