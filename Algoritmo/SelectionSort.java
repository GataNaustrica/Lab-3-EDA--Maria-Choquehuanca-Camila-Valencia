package algoritmos;

import modelos.Movie;
import java.util.ArrayList;
import java.util.Comparator;

public class SelectionSort {                             // Implementacion de Selection Sort para ArrayList<Movie>
    public static void sort(ArrayList<Movie> movies, Comparator<Movie> comparador) {      // Ordena 'movies' según 'comparador'
        int n = movies.size();                                                            // tamaño de la lista
        for (int i = 0; i < n - 1; i++) {                                                 // fija el inicio de la parte no ordenada
            int min_idx = i;                                                              // índice del mínimo encontrado
            for (int j = i + 1; j < n; j++) {                                             // recorre lo que queda de la lista
                if (comparador.compare(movies.get(j), movies.get(min_idx)) < 0) {         // si movies[j] < movies[min_idx]
                    min_idx = j;                                                         // actualiza el índice del mínimo
                }
            }
            Movie temp = movies.get(min_idx);                            // intercambia: coloca el mínimo en posición i
            movies.set(min_idx, movies.get(i));
            movies.set(i, temp);
        }
    }
}              // Propiedades: in-place; no estable; O(n^2) tiempo (mejor/promedio/peor);
 // O(1) espacio extra; realiza a lo más n-1 swaps.
