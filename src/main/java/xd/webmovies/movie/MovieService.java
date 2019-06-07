package xd.webmovies.movie;

import org.springframework.stereotype.Service;

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
        movieRepository.save(movie);
    }

    void deleteMovie(Long id){
        movieRepository.delete(movieRepository.getOne(id));
    }
}
