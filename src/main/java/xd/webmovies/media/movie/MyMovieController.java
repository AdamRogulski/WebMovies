package xd.webmovies.media.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xd.webmovies.media.MyDTO;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MyMovieController {

    @Autowired
    private MyMovieService myMovieService;

    @GetMapping("/mymovies")
    public List<MyMovie> getAllMyMovies(){
        return myMovieService.getAll();
    }

    @GetMapping("/mymovies/latest")
    public List<MyDTO> getLatestMyMovies(){
        return myMovieService.getLatest8Posts();
    }

    @PostMapping("/mymovies/{id}")
    public ResponseEntity<String> addMyMovie(@PathVariable Long id, @RequestBody MyDTO myDTO, Principal principal){

        myMovieService.saveMyMovie(id,myDTO,principal);
        return new ResponseEntity<>("Post added",HttpStatus.OK);
    }

    @DeleteMapping("/mymovies/{id}")
    public ResponseEntity<String> deleteMyMovie(@PathVariable Long id){
        if (myMovieService.getOne(id)!=null){
        myMovieService.deleteMyMovie(id);
        return new ResponseEntity<>("Post deleted",HttpStatus.OK);}

        else
            return new ResponseEntity<>("Can't find movie with this id",HttpStatus.BAD_REQUEST);
    }

}
