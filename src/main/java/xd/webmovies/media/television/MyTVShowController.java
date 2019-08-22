package xd.webmovies.media.television;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xd.webmovies.media.MyDTO;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MyTVShowController {

    @Autowired
    private MyTVShowService myTVShowService;

    @GetMapping("/mytvshows")
    public List<MyTVShow> getAllShows(){
        return myTVShowService.getAll();
    }

    @GetMapping("/mytvshows/latest")
    public List<MyDTO> getLatestShows(){
        return myTVShowService.getLatestShows();
    }

    @PostMapping("/mytvshows/{id}")
    public ResponseEntity<String> addShow(@PathVariable Long id, @RequestBody MyDTO myDTO, Principal principal){

        myTVShowService.saveMyShow(id, myDTO,principal);
        return new ResponseEntity<>("Added", HttpStatus.OK);
    }

    @DeleteMapping("/mytvshows/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable Long id){
        if(myTVShowService.getOne(id) != null){
        myTVShowService.deleteMyShow(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);}
        else
            return new ResponseEntity<>("Not found", HttpStatus.CONFLICT);
    }
}
