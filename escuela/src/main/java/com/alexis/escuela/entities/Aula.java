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
@Table(name = "AULAS") // Especifica el nombre exacto de la tabla en BD
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AULA")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 30)
    private String nombre;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    /*
    @Builder.Default
    @OneToMany(mappedBy = "aula")
    private List<Grupo> grupos new ArrayList<>();
     */


    public void actualizar (String nombre, Integer capacidad) {

        validarDatos(nombre, capacidad);

        this.nombre = nombre.trim();
        this.capacidad = capacidad;
    }

    private void validarDatos(String nombre, Integer capacidad) {

        StringCustomUtils.validarTamanio(nombre.trim(), 2, 30,
                "El nombre es requerido y debe tener entre 2 y 30 caracteres");

        if (capacidad == null || capacidad < 0)
            throw new IllegalArgumentException("La capacidad es requerida y debe ser positiva");

    }

}
