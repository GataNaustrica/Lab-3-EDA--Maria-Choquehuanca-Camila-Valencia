package util;

import modelos.Movie;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVuploader {

    public static ArrayList<Movie> loadMovies(String filePath) throws IOException, NumberFormatException {
        ArrayList<Movie> movies = new ArrayList<>();
        String line = "";
        
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        br.readLine();

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

            if (data.length < 15) {
                continue;
            }

            String title = data[1].trim().replaceAll("^\"|\"$", "");
            String releaseYearStr = data[2].trim().replaceAll("^\"|\"$", "");
            String genre = data[5].trim().replaceAll("^\"|\"$", "");
            String ratingStr = data[6].trim().replaceAll("^\"|\"$", "");
            String director = data[9].trim().replaceAll("^\"|\"$", "");

            if (title.isEmpty() || releaseYearStr.isEmpty() || genre.isEmpty() || ratingStr.isEmpty() || director.isEmpty()) {
                continue;
            }

            int releaseYear = Integer.parseInt(releaseYearStr);
            double rating = Double.parseDouble(ratingStr);

            Movie movie = new Movie(title, director, genre, releaseYear, rating);
            movies.add(movie);
        }
        
        br.close();
        return movies;
    }
}
