package xd.webmovies.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    List<Movie> showAllMovies(){
       return movieRepository.findAll();
    }

    void addMovie(Movie movie){
        if(movie.getDescription().isEmpty())
            movie.setDescription("Movie hasn't description yet");
        movieRepository.save(movie);
    }

    boolean isMovieHasUniqueTitle(String title){
       return movieRepository.existsByTitle(title);
    }

    void deleteMovie(Long id){
        movieRepository.delete(movieRepository.getOne(id));
    }

    Movie getOne(Long id){
        return movieRepository.getOne(id);
    }
}
