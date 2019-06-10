package xd.webmovies.movie;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping("/filmy")
    public List<Movie> getMovies(){
        return movieService.showAllMovies();
    }

    @GetMapping("/filmy/{id}")
    public Movie getOneMovie(@PathVariable Long id){
        return movieService.getOne(id);
    }

    @PostMapping("/filmy/dodaj")
    public ResponseEntity<String> addMovie(@RequestBody Movie movie){

        if(!movieService.isMovieHasUniqueTitle(movie.getTitle())) {
            movieService.addMovie(movie);
            return new ResponseEntity<>("Movie added", HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>("Movie is already in database",HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/filmy/usun")
    public void deleteMovie(@RequestParam Long id){
        movieService.deleteMovie(id);
    }
}
