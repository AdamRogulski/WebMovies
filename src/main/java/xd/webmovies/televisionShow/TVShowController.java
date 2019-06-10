package xd.webmovies.televisionShow;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TVShowController {

    private TVShowService tvShowService;

    public TVShowController(TVShowService tvShowService) {
        this.tvShowService = tvShowService;
    }

    @GetMapping("/seriale")
    public List<TVShow> getAll(){
       return tvShowService.getAllShows();
    }


    @PostMapping("/seriale/dodaj")
    public void addShow(@RequestBody TVShow tvShow){
        tvShowService.addShow(tvShow);
    }

}
