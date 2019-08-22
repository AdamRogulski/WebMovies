package xd.webmovies.media.television;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xd.webmovies.media.MediaDTO;
import xd.webmovies.media.MyDTO;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class TVShowController {

    @Autowired
    private TVShowService tvShowService;

    @GetMapping("/tvshows")
    public List<TVShow> getAll(){
        return  tvShowService.getAllShows();
    }

    @GetMapping("/tvshows/latest")
    public List<TVShow> getLatest10(){
        return tvShowService.get10LatestAddedShows();
    }

    @GetMapping("/tvshows/{id}")
    public TVShow getOneShow(@PathVariable Long id){
        return tvShowService.getOneTVShow(id);
    }

    @GetMapping("/tvshows/{id}/comments")
    public Set<MyDTO> getComments(@PathVariable Long id){

        return tvShowService.getComments(id);
    }

    @GetMapping("/tvshows/search")
    public List<MediaDTO> searchTVShows(@RequestParam String title){
        return tvShowService.searchTVShowsByTitle(title);
    }

    @PostMapping("/tvshows/add")
    public ResponseEntity<String> addShow(@RequestBody TVShowDTO tvShow){

        if(!tvShowService.isTitleNotUnique(tvShow.getTitle())){
        tvShowService.addShow(tvShow);
            return new ResponseEntity<>("TV Show added", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("TV Show with this title is already in database", HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/tvshows/{id}")
    public ResponseEntity<String> updateShow(@PathVariable Long id, @RequestBody TVShowDTO tvShowDTO){

        if (tvShowService.getOneTVShow(id)!=null){
        tvShowService.updateShow(id,tvShowDTO);
        return new ResponseEntity<>("TV show updated",HttpStatus.OK);}
        else
            return new ResponseEntity<>("Can't find TV Show with this id to update",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/tvshows/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable Long id){

        if(tvShowService.getOneTVShow(id)!=null){
        tvShowService.deleteTVShow(id);
        return new ResponseEntity<>("Tv show: " +id +" deleted", HttpStatus.OK);}
        else
            return new ResponseEntity<>("Can't find TV Show with that id", HttpStatus.NOT_FOUND);
    }

}
