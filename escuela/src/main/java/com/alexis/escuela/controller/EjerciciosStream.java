package com.alexis.escuela.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stream")
public class EjerciciosStream {

    // 1. Obtener los 3 números más grandes de una lista
    @GetMapping("/top3")
    public ResponseEntity<List<Integer>> top3Numbers() {

        List<Integer> numeros = Arrays.asList(10, 45, 2, 89, 33, 76, 1);

        return ResponseEntity.ok(numeros.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3).toList()
        );
    }

    // 2. Contar cuántas veces se repite cada palabra
    // Resultado esperado: {java=3, python=2, c=1}
    @GetMapping("/count-words")
    public ResponseEntity<Map<String, Long>> countWords() {

        List<String> palabras = Arrays.asList("java", "python", "java", "c", "python", "java");

        return ResponseEntity.ok(palabras.stream()
                .collect(Collectors.groupingBy(palabra -> palabra, Collectors.counting()))
        );
    }

    // 3. Obtener lista sin duplicados en orden alfabético
    @GetMapping("/unique-names")
    public ResponseEntity<List<String>> uniqueNames() {

        List<String> nombres = Arrays.asList("Carlos", "Ana", "Pedro", "Ana", "Carlos");

        return ResponseEntity.ok(nombres.stream()
                .distinct()
                .sorted().toList()
        );
    }

    // 4. Obtener el segundo número más pequeño
    @GetMapping("/second-smallest")
    public ResponseEntity<Integer> secondSmallest() {

        List<Integer> numeros = Arrays.asList(8, 3, 10, 1, 6);

        return ResponseEntity.ok(numeros.stream()
                .sorted()
                .skip(1)
                .findFirst().orElse(null)
        );
    }

    // 5. Concatenar los nombres en una sola cadena separados por coma
    @GetMapping("/concat-names")
    public ResponseEntity<String> concatNames() {

        List<String> nombres = Arrays.asList("Ana", "Luis", "Pedro");

        return ResponseEntity.ok(nombres.stream()
                .collect(Collectors.joining(","))
        );
    }

    // 6. Filtrar strings que empiecen con vocal y devolver en mayúsculas
    @GetMapping("/vowels-uppercase")
    public ResponseEntity<List<String>> vowelsUppercase() {

        List<String> palabras = Arrays.asList("elefante", "tigre", "iguana", "oso", "mono");

        return ResponseEntity.ok(palabras.stream()
                .filter(p -> p.toLowerCase().matches("^[aeiou].*"))
                .map(String::toUpperCase).toList()
        );
    }

    // 7. Separar números en pares e impares en un Map
    @GetMapping("/even-odd")
    public ResponseEntity<Map<String, List<Integer>>> evenOdd() {

        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6);

        /*return ResponseEntity.ok(numeros.stream()
                .collect(Collectors.groupingBy(num -> num % 2 == 0 ? "Pares" : "Impares"))
        );*/

        Map<String, List<Integer>> resultado = new HashMap<>();
        resultado.put("Pares", numeros.stream().filter(n -> n % 2 == 0).toList());
        resultado.put("Impares", numeros.stream().filter(n -> n % 2 != 0).toList());

        return ResponseEntity.ok(resultado);
    }

    // 8. Agrupar palabras por su primera letra
    @GetMapping("/group-by-first-letter")
    public ResponseEntity<Map<String, List<String>>> groupByFirstLetter() {

        List<String> palabras = Arrays.asList("perro", "pato", "gato", "gallo", "rana");

        return ResponseEntity.ok(palabras.stream()
                .collect(Collectors.groupingBy(p -> p.substring(0, 1)))
        );
    }

    // 9. Obtener los strings más largos
    @GetMapping("/longest-strings")
    public ResponseEntity<List<String>> longestStrings() {

        List<String> palabras = Arrays.asList("sol", "luna", "estrella", "mar");

        int maxima = palabras.stream()
                .mapToInt(String::length)
                .max().orElse(0);

        return ResponseEntity.ok(palabras.stream()
                .filter(p -> p.length() == maxima).toList()
        );
    }

    // 10. Eliminar elementos vacíos o nulos
    @GetMapping("/remove-empty")
    public ResponseEntity<List<String>> removeEmpty() {

        List<String> datos = Arrays.asList("hola", "", null, "adiós", " ");

        return ResponseEntity.ok(datos.stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.isBlank()).toList()
        );
    }
}