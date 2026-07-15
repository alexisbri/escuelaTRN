package com.alexis.escuela.entities;


import com.alexis.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // Marca la clase como entidad JPA para mapear a tabla de BD.
@Builder // Permite construir objetos usando el patrón builder.
@NoArgsConstructor // Constructor vacío.
@AllArgsConstructor // Constructor con todos los campos (requerido por @Builder).
@Getter // Genera getters para todos los campos.
@Table(name = "CURSOS") // Especifica el nombre exacto de la tabla en BD
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    @Column(name = "CREDITOS", nullable = false)
    private Integer creditos;

    public void actualizar(String nombre, String descripcion, Integer creditos) {

        validarDatos(nombre, descripcion, creditos);

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creditos = creditos;
    }

    private void validarDatos(String nombre, String descripcion, Integer creditos) {

        StringCustomUtils.validarTamanio(nombre, 4, 100,
                "El nombre es requerido y debe tener entre 4 y 100 caracteres");

        StringCustomUtils.validarTamanio(descripcion, 10, 200,
                "La descripcion es requerido y debe tener entre 10 y 200 caracteres");

        if (creditos == null || creditos < 0)
            throw new IllegalArgumentException("Los creditos son requeridos y deben ser positivos");

    }

}
