package xd.webmovies.media.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xd.webmovies.media.MediaDTO;
import xd.webmovies.media.MyDTO;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public List<Movie> getAllMovies(){
        return movieService.showAllMovies();
    }

    @GetMapping("/movies/latest")
    public List<Movie> getLatest10Movie(){
        return movieService.getLatest10Movies();
    }

    @GetMapping("/movies/{id}")
    public Movie getOneMovie(@PathVariable Long id){
        return movieService.getOne(id);
    }

    @GetMapping("/movies/{id}/comments")
    public Set<MyDTO> getComments(@PathVariable Long id){
        return movieService.getComments(id);
    }

    @GetMapping("/movies/search")
    public List<MediaDTO> searchMovies(@RequestParam String title){
        return movieService.searchMovieByTitle(title);
    }

    @PostMapping("/movies/add")
    public ResponseEntity<String> addMovie(@RequestBody MediaDTO movie){

        if(!movieService.isTitleNotUnique(movie.getTitle())){
            movieService.addMovie(movie);
            return new ResponseEntity<>("Movie added", HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>("Movie with this title is already in database", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/movies/{id}")
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

    @PutMapping("movies/{id}")
    public ResponseEntity<String> updateMovie(@PathVariable Long id, @RequestBody MediaDTO updatedMovie){

        movieService.updateMovie(id,updatedMovie);

        return new ResponseEntity<>("Movie updated",HttpStatus.OK);
    }
}
