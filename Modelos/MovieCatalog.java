package modelos;

import algoritmos.InsertionSort;
import algoritmos.MergeSort;
import algoritmos.QuickSort;
import algoritmos.RadixSort;
import algoritmos.SelectionSort;
import comparadores.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MovieCatalog {                       // Catálogo que mantiene una lista de Movie y recuerda por qué atributo quedó ordenada

    private ArrayList<Movie> movies;            // almacenamiento principal de películas
    private String sortedByAttribute;                     // atributo por el que actualmente está ordenada la lista

    public MovieCatalog(ArrayList<Movie> movies) {           // inicia con la lista dada y sin orden “recordado”
        this.movies = movies;
        this.sortedByAttribute = null;
    }

    public void sortByAlgorithm(String algorithm, String attribute) {       // ordena según algoritmo y atributo elegidos
        Comparator<Movie> comparador;                                         // comparador según el atributo

        switch (attribute.toLowerCase()) {                                  // selecciona comparador por atributo
            case "director":
                comparador = new DirectorComparador();                    // compara por director
                break;
            case "genre":
                comparador = new GeneroComparador();                      // compara por género
                break;
            case "year":
                comparador = new AnnioComparador();                     // compara por año de lanzamiento
                break;
            case "rating":
            default:
                comparador = new RatingComparador();                         // por defecto, compara por rating
                attribute = "rating";                                   // normaliza el nombre del atributo
                break;
        }

        switch (algorithm.toLowerCase()) {                            // elige algoritmo de ordenamiento
            case "insertionsort":
                InsertionSort.sort(this.movies, comparador);                        // in-place, bueno para listas casi ordenadas
                break;
            case "selectionsort":
                SelectionSort.sort(this.movies, comparador);      // in-place, pocos swaps, O(n^2)
                break;
            case "mergesort":
                MergeSort.sort(this.movies, comparador);                       // estable, O(n log n), usa memoria extra
                break;
            case "quicksort":
                QuickSort.sort(this.movies, comparador);                   // promedio O(n log n), in-place
                break;
            case "radixsort":
                // radixSort solo ordena por rating
                RadixSort.sortByRating(this.movies);      // usa radix (LSD) sobre rating (escalado a enteros)
                
                // se fuerza a que el atributo sea rating, para luego asignar correctamente
                attribute = "rating";                                                    // asegura coherencia con el campo sortedByAttribute
                break;
            default:
                Collections.sort(this.movies, comparador);                               // fallback: sort de Java con el comparador elegido
                break;
        }

        // Esta línea ahora SIEMPRE asignará "rating" si se usó RadixSort
        this.sortedByAttribute = attribute;                                              // guarda el atributo por el que quedó ordenada la lista
    }

    public ArrayList<Movie> getMoviesByRating(double rating) {                           // devuelve todas las películas con rating ≈ dado
    ArrayList<Movie> result = new ArrayList<>();
    double tolerance = 0.001;

    if ("rating".equals(sortedByAttribute)) {                                            // si está ordenado por rating, aprovecha búsqueda binaria
        //busqueda binaria (solo para encontrar un punto de inicio)
        int index = Collections.binarySearch(movies, new Movie(null, null, null, 0, rating),
                new RatingComparador());
        
        int startIndex;
        if (index >= 0) {
            // si se encuentra un match exacto, empieza ahí
            startIndex = index;
        } else {
            // si NO, se calcula el punto de inserción
            // este es el indice del primer elemento MÁS GRANDE que el 'rating'
            startIndex = -(index + 1);
        }

        // se escanea a la IZQUIERDA (para ratings menores)
        // se empieza desde el índice ANTERIOR al startIndex
        for (int i = startIndex - 1; i >= 0; i--) {
            // ahora se aplica la logica de tolerancia
            if (Math.abs(movies.get(i).getRating() - rating) <= tolerance) {
                result.add(movies.get(i));
            } else {
                // si se sale de la tolerancia, break (la lista esta ordenada)
                break; 
            }
        }

        // escanea a la derecha (ratings mayores)
        // se empieza desde startIndex (podría estar en tolerancia)
        for (int i = startIndex; i < movies.size(); i++) {
            //lógica de tolerancia
            if (Math.abs(movies.get(i).getRating() - rating) <= tolerance) {
                result.add(movies.get(i));
            } else {
                // si se sale de la tolerancia, break
                break;
            }
        }

    }
    else {
        // busqueda lineal
        for (Movie movie : this.movies) {
            if (Math.abs(movie.getRating() - rating) <= tolerance) {
                result.add(movie);
                }
            }
        }
        return result;                                                                    // retorna todas las coincidencias dentro de la tolerancia
    }

    public ArrayList<Movie> getMoviesByDirector(String director) {                        // busca por director (binaria si ya está ordenado por director)
        ArrayList<Movie> result = new ArrayList<>();
        if ("director".equals(sortedByAttribute)) {                                       // si está ordenado por director, usar binarySearch y expandir vecinos
            int index = Collections.binarySearch(movies, new Movie(null, director, null, 0, 0),
                    new DirectorComparador());
            if (index >= 0) {                                                             // si encontró uno, agrega y explora contiguos iguales
                result.add(movies.get(index));
                for (int i = index - 1; i >= 0 && movies.get(i).getDirector().equals(director); i--) {
                    result.add(movies.get(i));
                }
                for (int i = index + 1; i < movies.size() && movies.get(i).getDirector().equals(director); i++) {
                    result.add(movies.get(i));
                }
            }
        } else {                                                                           // si no está ordenado por director, recorre linealmente
            for (Movie movie : this.movies) {
                if (movie.getDirector().equals(director)) {
                    result.add(movie);
                }
            }
        }
        return result;                                                                     // devuelve todas las películas del director
    }

    public ArrayList<Movie> getMoviesByGenre(String genre) {                               // busca por género (binaria si ya está ordenado por género)
        ArrayList<Movie> result = new ArrayList<>();
        if ("genre".equals(sortedByAttribute)) {                                           // aprovecha orden y expande vecinos iguales
            int index = Collections.binarySearch(movies, new Movie(null, null, genre, 0, 0), new GeneroComparador());
            if (index >= 0) {
                result.add(movies.get(index));
                for (int i = index - 1; i >= 0 && movies.get(i).getGenre().equals(genre); i--) {
                    result.add(movies.get(i));
                }
                for (int i = index + 1; i < movies.size() && movies.get(i).getGenre().equals(genre); i++) {
                    result.add(movies.get(i));
                }
            }
        } else {                                                                           // si no está ordenado por género, búsqueda lineal
            for (Movie movie : this.movies) {
                if (movie.getGenre().equals(genre)) {
                    result.add(movie);
                }
            }
        }
        return result;                                                                     // devuelve todas las películas del género
    }

    public ArrayList<Movie> getMoviesByYear(int year) {                                    // busca por año (binaria si ya está ordenado por año)
        ArrayList<Movie> result = new ArrayList<>();
        if ("year".equals(sortedByAttribute)) {                                            // si está ordenado por año, binarySearch + expandir
            int index = Collections.binarySearch(movies, new Movie(null, null, null, year, 0), new AnnioComparador());
            if (index >= 0) {
                result.add(movies.get(index));
                for (int i = index - 1; i >= 0 && movies.get(i).getReleaseYear() == year; i--) {
                    result.add(movies.get(i));
                }
                for (int i = index + 1; i < movies.size() && movies.get(i).getReleaseYear() == year; i++) {
                    result.add(movies.get(i));
                }
            }
        } else {                                                                           // si no está ordenado por año, búsqueda lineal
            for (Movie movie : this.movies) {
                if (movie.getReleaseYear() == year) {
                    result.add(movie);
                }
            }
        }
        return result;                                                                     // devuelve todas las películas del año dado
    }
}                                                                                          // Notas: mantener 'sortedByAttribute' coherente permite búsquedas rápidas;
 // si la lista cambia (add/remove), conviene resortear o invalidar el atributo.
