package comparadores;

import modelos.Movie;
import java.util.Comparator;

public class RatingComparador implements Comparator<Movie> {

    @Override
    public int compare(Movie m1, Movie m2) {
        return Double.compare(m1.getRating(), m2.getRating());
    }
}