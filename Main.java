package main;

import modelos.Movie;
import modelos.MovieCatalog;
import util.CSVuploader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String path = "EDA-2/data/imdb_top_1000.csv";
        ArrayList<Movie> movies = CSVuploader.loadMovies(path);

        System.out.println("Se cargaron " + movies.size() + " películas.\n");

        Collections.shuffle(movies);

        if (movies.isEmpty()) {
            System.err.println("No se cargaron películas.");
            return;
        }

        exp1(movies);
        exp2(movies);
    }

    private static void exp1(ArrayList<Movie> movies) {
        System.out.println("Experimento 1");

        int reps = 8000;
        System.out.println("NOTA: Cada medición se promedia entre " + reps + " ejecuciones.");

        System.out.println("\n InsertionSort vs. SelectionSort (por Rating)");
        System.out.printf("%-10s | %-18s | %-18s%n", "Tamaño (n)", "InsertionSort (ns)", "SelectionSort (ns)");
        System.out.println(String.join("", Collections.nCopies(55, "-")));

        for (int n = 100; n <= movies.size(); n += 100) {
            long totalDur1 = 0;
            for (int i = 0; i < reps; i++) {
                ArrayList<Movie> subList = new ArrayList<>(movies.subList(0, n));
                MovieCatalog catalog = new MovieCatalog(subList);
                long start = System.nanoTime();
                catalog.sortByAlgorithm("InsertionSort", "rating");
                totalDur1 += (System.nanoTime() - start);
            }
            long avgDur1 = totalDur1 / reps;

            long totalDur2 = 0;
            for (int i = 0; i < reps; i++) {
                ArrayList<Movie> subList = new ArrayList<>(movies.subList(0, n));
                MovieCatalog catalog = new MovieCatalog(subList);
                long start = System.nanoTime();
                catalog.sortByAlgorithm("SelectionSort", "rating");
                totalDur2 += (System.nanoTime() - start);
            }
            long avgDur2 = totalDur2 / reps;

            System.out.printf("%-10d | %-18d | %-18d%n", n, avgDur1, avgDur2);
        }

        System.out.println("\n MergeSort vs. QuickSort");
        System.out.printf("%-10s | %-15s | %-15s%n", "Tamaño (n)", "MergeSort (ns)", "QuickSort (ns)");
        System.out.println(String.join("", Collections.nCopies(50, "-")));

        for (int n = 100; n <= movies.size(); n += 100) {
            long totalDur1 = 0;
            for (int i = 0; i < reps; i++) {
                ArrayList<Movie> subList = new ArrayList<>(movies.subList(0, n));
                MovieCatalog catalog = new MovieCatalog(subList);
                long start = System.nanoTime();
                catalog.sortByAlgorithm("MergeSort", "rating");
                totalDur1 += (System.nanoTime() - start);
            }
            long avgDur1 = totalDur1 / reps;

            long totalDur2 = 0;
            for (int i = 0; i < reps; i++) {
                ArrayList<Movie> subList = new ArrayList<>(movies.subList(0, n));
                MovieCatalog catalog = new MovieCatalog(subList);
                long start = System.nanoTime();
                catalog.sortByAlgorithm("QuickSort", "rating");
                totalDur2 += (System.nanoTime() - start);
            }
            long avgDur2 = totalDur2 / reps;

            System.out.printf("%-10d | %-15d | %-15d%n", n, avgDur1, avgDur2);
        }
        System.out.println("\n RadixSort (Rating x10)");
        System.out.printf("%-10s | %-15s%n", "Tamaño (n)", "RadixSort (ns)");
        System.out.println(String.join("", Collections.nCopies(30, "-")));

        for (int n = 100; n <= movies.size(); n += 100) {
            long totalDur = 0;
            for (int i = 0; i < reps; i++) {
                ArrayList<Movie> subList = new ArrayList<>(movies.subList(0, n));
                MovieCatalog catalog = new MovieCatalog(subList);
                long start = System.nanoTime();
                catalog.sortByAlgorithm("RadixSort", "rating");
                totalDur += (System.nanoTime() - start);
            }
            long avgDur = totalDur / reps;
            System.out.printf("%-10d | %-15d%n", n, avgDur);
        }
    }

    private static void exp2(ArrayList<Movie> movies) {
        System.out.println("\n Experimento 2:");
        System.out.printf("%-10s | %-25s | %-20s | %-20s%n", "Tamaño (n)", "Director", "T. Lineal (ns)",
                "T. Binaria (ns)");
        System.out.println(String.join("", Collections.nCopies(85, "-")));

        List<String> directors = Arrays.asList(
                "Christopher Nolan", "Quentin Tarantino", "Steven Spielberg", "Hayao Miyazaki", "Roman Polanski");

        int reps = 8000;

        for (int n = 100; n <= movies.size(); n += 100) {

            for (int j = 0; j < directors.size(); j++) {
                String director = directors.get(j);

                ArrayList<Movie> subList = new ArrayList<>(movies.subList(0, n));
                MovieCatalog catalog = new MovieCatalog(subList);

                long startLinear = System.nanoTime();
                for (int i = 0; i < reps; i++) {
                    catalog.getMoviesByDirector(director);
                }
                long totalLinear = System.nanoTime() - startLinear;
                long avgLinear = totalLinear / reps;

                catalog.sortByAlgorithm("MergeSort", "director");

                long startBinary = System.nanoTime();
                for (int i = 0; i < reps; i++) {
                    catalog.getMoviesByDirector(director);
                }
                long totalBinary = System.nanoTime() - startBinary;
                long avgBinary = totalBinary / reps;

                System.out.printf("%-10d | %-25s | %-20d | %-20d%n", n, director, avgLinear, avgBinary);
            }
            System.out.println(String.join("", Collections.nCopies(85, "-")));
        }
    }
}