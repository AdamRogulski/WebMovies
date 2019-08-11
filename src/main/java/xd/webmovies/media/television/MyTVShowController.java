package xd.webmovies.media.television;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xd.webmovies.media.MyDTO;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MyTVShowController {

    @Autowired
    MyTVShowService myTVShowService;


    @GetMapping("/getmyshows")
    public List<MyTVShow> getAllShows(){
        return myTVShowService.getAll();
    }

    @PostMapping("/addmyshow/{id}")
    public ResponseEntity<String> addShow(@PathVariable Long id, @RequestBody MyDTO myDTO, Principal principal){


        myTVShowService.saveMyShow(id, myDTO,principal);

        return new ResponseEntity<>("Show added", HttpStatus.OK);
    }

    @DeleteMapping("/deletemyshow/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable Long id){
        if(myTVShowService.getOne(id) != null){
        myTVShowService.deleteMyShow(id);
        return new ResponseEntity<>("Usunięto",HttpStatus.OK);}
        else
            return new ResponseEntity<>("Nie znaleziono", HttpStatus.CONFLICT);
    }
}
