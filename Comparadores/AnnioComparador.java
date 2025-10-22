package comparadores;

import modelos.Movie;
import java.util.Comparator;

public class AnnioComparador implements Comparator<Movie> {

    @Override
    public int compare(Movie m1, Movie m2) {
        return Integer.compare(m1.getReleaseYear(), m2.getReleaseYear());
    }
}
