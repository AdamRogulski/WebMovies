package xd.webmovies.movie;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/filmy")
    public List<Movie> getMovies(){
        return movieService.showAllMovies();
    }

    @PostMapping("/filmy/dodaj")
    public void addMovie(@RequestBody Movie movie){
        movieService.addMovie(movie);
    }

    @DeleteMapping("/filmy/usun")
    public void deleteMovie(@RequestParam Long id){
        movieService.deleteMovie(id);
    }
}
