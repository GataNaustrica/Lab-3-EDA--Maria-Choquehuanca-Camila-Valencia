package algoritmos;

import modelos.Movie;
import java.util.ArrayList;
import java.util.Comparator;

public class InsertionSort {                                                              // Implementacion Insertion Sort para ArrayList<Movie>

    public static void sort(ArrayList<Movie> movies, Comparator<Movie> comparador) {      // Ordena 'movies' según 'comparador'
        int n = movies.size();                                                            // tamaño de la lista
        for (int i = 1; i < n; ++i) {                                                     // recorre desde el segundo elemento
            Movie key = movies.get(i);                                                   // 'key' = elemento a insertar en la parte ordenada
            int j = i - 1;                                                           // índice para desplazar hacia atrás

            while (j >= 0 && comparador.compare(movies.get(j), key) > 0) {      // mientras el anterior sea mayor que 'key'
                movies.set(j + 1, movies.get(j));                                // desplaza el elemento a la derecha
                j = j - 1;                                                          // avanza hacia la izquierda
            }
            movies.set(j + 1, key);                                                  // inserta 'key' en su posición correcta
        }
    }
}            // Propiedades: in-place, estable si el comparador respeta igualdad,
            // mejor caso O(n) (ya ordenado), peor/promedio O(n^2).
