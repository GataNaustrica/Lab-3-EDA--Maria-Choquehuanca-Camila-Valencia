package algoritmos;

import modelos.Movie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class QuickSort {                                                         // Implementa QuickSort para ArrayList<Movie>

    private static int partition(ArrayList<Movie> movies, int low, int high,        // Partición Lomuto: pivote al final
     Comparator<Movie> comparador) {
        Movie pivot = movies.get(high);                                          // pivote = último elemento
        int i = (low - 1);                                                          // i: límite de la zona <= pivote
        for (int j = low; j < high; j++) {                                        // recorre elementos [low, high-1]
            if (comparador.compare(movies.get(j), pivot) <= 0) {                  // si movies[j] <= pivote
                i++;                                                                  // expandimos zona <= pivote
                Collections.swap(movies, i, j);                               // colocamos movies[j] a la izquierda
            }
        }
        Collections.swap(movies, i + 1, high);                               // pone el pivote en su lugar final
        return i + 1;                                                      // índice final del pivote (partición)
    }

    private static void quickSort(ArrayList<Movie> movies, int low, int high,            // QuickSort recursivo in-place
    Comparator<Movie> comparador) {
        if (low < high) {                                                                // condición de recursión
            int pi = partition(movies, low, high, comparador);                           // particiona y obtiene pos. pivote
            quickSort(movies, low, pi - 1, comparador);                                  // ordena subarreglo izquierdo
            quickSort(movies, pi + 1, high, comparador);                                 // ordena subarreglo derecho
        }
    }

    public static void sort(ArrayList<Movie> movies, Comparator<Movie> comparador) {     // API pública: ordena toda la lista
        quickSort(movies, 0, movies.size() - 1, comparador);                             // llama con los límites iniciales
    }
}                                                                                        // Propiedades: promedio O(n log n), peor O(n^2);
 // in-place; no estable (puede reordenar iguales).
