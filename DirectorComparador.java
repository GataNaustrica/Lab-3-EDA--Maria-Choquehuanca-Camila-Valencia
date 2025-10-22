package comparadores;

import modelos.Movie;
import java.util.Comparator;

public class DirectorComparador implements Comparator<Movie> {

    @Override
    public int compare(Movie m1, Movie m2) {
        return m1.getDirector().compareTo(m2.getDirector());
    }
}