package xd.webmovies.televisionShow;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TVShowController {

    private TVShowService tvShowService;

    public TVShowController(TVShowService tvShowService) {
        this.tvShowService = tvShowService;
    }

    @GetMapping("/seriale")
    public List<TVShow> getAll(){
       return tvShowService.getAllShows();
    }

    @GetMapping("/seriale/{id}")
    public TVShow getOneShow(@PathVariable Long id){
        return tvShowService.getOneTVShow(id);
    }

    @PostMapping("/seriale/dodaj")
    public ResponseEntity<String> addShow(@RequestBody TVShow tvShow){

        tvShowService.addShow(tvShow);
        return new ResponseEntity<>("TV Show added", HttpStatus.OK);
    }

    @DeleteMapping("/seriale/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable Long id){

        tvShowService.deleteTVShow(id);
        return new ResponseEntity<>("Tv Show with id: " +id +" deleted", HttpStatus.OK);
    }

}
