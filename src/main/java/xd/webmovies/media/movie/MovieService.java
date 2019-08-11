package xd.webmovies.media.movie;

import org.springframework.stereotype.Service;
import xd.webmovies.media.MediaDTO;

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

    void addMovie(MediaDTO movieDTO){

        Movie movie = new Movie();

        movie.setImage(movieDTO.getImage());
        movie.setDescription(movieDTO.getDescription());
        movie.setYear(movieDTO.getYear());
        movie.setTitle(movieDTO.getTitle());

            if (movie.getDescription()==null)
                movie.setDescription("Movie hasn't description yet");

            if(movie.getYear() < 0){
                movie.setYear(0);
                System.out.println("Year must be postitve number, setting year to default number 0");
            }
            if(movie.getYear()<1800 || movie.getYear()>2100 ){
                movie.setYear(0);
                System.out.println("Year must have value between 1800 and 2100, setting year to default number 0");
            }

            movieRepository.save(movie);

        }

    void deleteMovie(Long id){
        movieRepository.deleteById(id);
    }

    boolean isTitleNotUnique(String title){
        return movieRepository.existsByTitle(title);
    }

    Movie getOne(Long id){
        return movieRepository.findById(id).orElse(null);
    }

    void updateMovie(Long id,MediaDTO movieDTO){

        Movie oldMovie = movieRepository.getOne(id);

        oldMovie.setTitle(movieDTO.getTitle());
        oldMovie.setYear(movieDTO.getYear());
        oldMovie.setDescription(movieDTO.getDescription());
        oldMovie.setImage(movieDTO.getImage());

        movieRepository.save(oldMovie);
    }
}
