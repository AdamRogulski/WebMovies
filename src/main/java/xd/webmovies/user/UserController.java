package xd.webmovies.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xd.webmovies.media.movie.MyMovie;
import xd.webmovies.media.television.MyTVShow;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> showUsers(){
       return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getOneUser(id);
    }

    @GetMapping("/user/seriale/activity")
    public Set<MyTVShow> getUserShows(Principal principal){
        return userService.getUserShows(principal.getName());
    }

    @GetMapping("/user/filmy/activity")
    public Set<MyMovie> getUserMovies(Principal principal){
        return userService.getUserMovies(principal.getName());
    }

    @PostMapping("/user/add")
    public void  addUser(@RequestBody User user){
        userService.addUser(user);
    }
}
