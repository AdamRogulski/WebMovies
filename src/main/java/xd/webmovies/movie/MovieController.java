package xd.webmovies.movie;

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

        if(!movieService.isTitleNotUnique(movie.getTitle())){

            movieService.addMovie(movie);
            return new ResponseEntity<>("Movie added", HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>("Movie with this title is already in database", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/filmy/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id){

        if(movieService.getOne(id)==null)
            return new ResponseEntity<>("Can't find movie with id: "+id,HttpStatus.BAD_REQUEST);
        if(id<0)
            return new ResponseEntity<>("Id must be positive number",HttpStatus.BAD_REQUEST);
        else {
                movieService.deleteMovie(id);
                return new ResponseEntity<>("Movie with id: "+id +" deleted", HttpStatus.OK);
        }
    }

    @PutMapping("filmy/{id}")
    public ResponseEntity<String> updateMovie(@PathVariable Long id, @RequestBody Movie updatedMovie){

        Movie oldMovie = movieService.getOne(id);

        oldMovie.setTitle(updatedMovie.getTitle());
        oldMovie.setYear(updatedMovie.getYear());
        oldMovie.setDescription(updatedMovie.getDescription());
        oldMovie.setImage(updatedMovie.getImage());
        movieService.addMovie(oldMovie);

        return new ResponseEntity<>("Movie updated",HttpStatus.OK);
    }
}
