package xd.webmovies.media.television;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xd.webmovies.media.MyDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @GetMapping("/seriale/{id}/comments")
    public Set<MyDTO> getComments(@PathVariable Long id){
        TVShow tvShow = tvShowService.getOneTVShow(id);
        Set<MyDTO> myDTOS = new HashSet<>();

        for(MyTVShow x :tvShow.getMyShows()){
            MyDTO myDTO1 = new MyDTO();
            myDTO1.setComment(x.getComment());
            myDTO1.setRating(x.getRate());
            myDTO1.setStatus(x.getStatus());
            myDTOS.add(myDTO1);
        }
        return myDTOS;
    }

    @PostMapping("/seriale/dodaj")
    public ResponseEntity<String> addShow(@RequestBody TVShowDTO tvShow){

        tvShowService.addShow(tvShow);
        return new ResponseEntity<>("TV Show added", HttpStatus.OK);
    }

    @PutMapping("/seriale/{id}")
    public ResponseEntity<String> updateShow(@PathVariable Long id, @RequestBody TVShowDTO tvShowDTO){

        tvShowService.updateShow(id,tvShowDTO);
        return new ResponseEntity<>("TV Show updated",HttpStatus.OK);
    }

    @DeleteMapping("/seriale/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable Long id){

        tvShowService.deleteTVShow(id);
        return new ResponseEntity<>("Tv Show with id: " +id +" deleted", HttpStatus.OK);
    }

}
