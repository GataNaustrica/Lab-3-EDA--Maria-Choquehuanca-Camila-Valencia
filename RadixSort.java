package algoritmos;

import modelos.Movie;
import java.util.ArrayList;
import java.util.Arrays;

public class RadixSort {                // Radix Sort (LSD) para ordenar por rating (una cifra decimal)

    private static int getMaxRating(ArrayList<Movie> movies) {                            // obtiene el rating máximo como entero (x10)
        int max = 0;
        for (Movie movie : movies) {
            int ratingAsInt = (int) (movie.getRating() * 10);                             // escala: 7.8 -> 78 (trabajamos con enteros)
            if (ratingAsInt > max) {
                max = ratingAsInt;                                                       // guarda el mayor
            }
        }
        return max;                                                                       // devuelve el máximo escalado
    }

    private static void countingSort(ArrayList<Movie> movies, int exp) {                  // counting sort estable por dígito (exp = 1,10,100,...)
        int n = movies.size();
        Movie[] output = new Movie[n];                                                    // arreglo auxiliar de salida
        int[] count = new int[10];                                                        // conteo para dígitos 0..9
        Arrays.fill(count, 0);

        // Multipliqué por 10 para que los ratings se manejen como enteros, no me corre si uso decimales
        for (int i = 0; i < n; i++) {                                                     // cuenta frecuencia del dígito actual
            int ratingAsInt = (int) (movies.get(i).getRating() * 10);                     // rating escalado
            count[(ratingAsInt / exp) % 10]++;                                            // extrae dígito (unidad, decena, ...)
        }

        for (int i = 1; i < 10; i++) {                                                    // prefijos acumulados -> posiciones finales
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {                                                // llena 'output' de derecha a izquierda (estable)
            int ratingAsInt = (int) (movies.get(i).getRating() * 10);
            output[count[(ratingAsInt / exp) % 10] - 1] = movies.get(i);                  // coloca en su índice final
            count[(ratingAsInt / exp) % 10]--;                                            // decrementa el contador
        }

        for (int i = 0; i < n; i++) {                                                     // copia de vuelta al ArrayList original
            movies.set(i, output[i]);
        }
    }

    public static void sortByRating(ArrayList<Movie> movies) {                            // API pública: ordena por rating ascendente
        int m = getMaxRating(movies);                                                     // rating máximo (escalado)
        for (int exp = 1; m / exp > 0; exp *= 10) {                                       // procesa dígitos LSD->MSD: 1,10,100,...
            countingSort(movies, exp);                                                    // aplica counting sort por cada dígito
        }
    }
}                                // Propiedades: estable; tiempo O(d*(n + k)) con base 10 (k=10);
// espacio O(n + k); requiere ratings >= 0 y compara sólo por rating.
