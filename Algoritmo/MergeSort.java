package algoritmos;

import modelos.Movie;
import java.util.ArrayList;
import java.util.Comparator;

public class MergeSort {                                                             

    public static void sort(ArrayList<Movie> movies, Comparator<Movie> comparador) {     // ordena la lista 'movies' según 'comparador'
        if (movies.size() <= 1) {                                                        // caso base: 0 o 1 elemento ya está ordenado
            return;                                                                      // termina sin hacer nada
        }

        int mid = movies.size() / 2;                                                     // punto medio
        ArrayList<Movie> left  = new ArrayList<>(movies.subList(0, mid));               // mitad izquierda (copia)
        ArrayList<Movie> right = new ArrayList<>(movies.subList(mid, movies.size()));   // mitad derecha (copia)

        sort(left, comparador);                                                          // ordena recursivamente izquierda
        sort(right, comparador);                                                         // ordena recursivamente derecha

        merge(movies, left, right, comparador);                                          // fusiona mitades ordenadas en 'movies'
    }

    private static void merge(ArrayList<Movie> mainList,ArrayList<Movie> left,ArrayList<Movie> right,Comparator<Movie> comparador) {                                 // mezcla ordenada estable
                                
        int i = 0, j = 0, k = 0;                                                         // i: índice left, j: índice right, k: índice mainList

        while (i < left.size() && j < right.size()) {                                    // mientras haya elementos en ambas mitades
            if (comparador.compare(left.get(i), right.get(j)) <= 0) {                    // si left[i] <= right[j] según comparador
                mainList.set(k++, left.get(i++));                                        // coloca left[i] y avanza i y k
            } else {                                                                      // si right[j] < left[i]
                mainList.set(k++, right.get(j++));                                       // coloca right[j] y avanza j y k
            }
        }

        while (i < left.size()) {                                                        // si sobran elementos en left
            mainList.set(k++, left.get(i++));                                            // cópialos todos al final
        }

        while (j < right.size()) {                                                       // si sobran elementos en right
            mainList.set(k++, right.get(j++));                                           // cópialos todos al final
        }
    }
}
