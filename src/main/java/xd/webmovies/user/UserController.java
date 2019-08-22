package xd.webmovies.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xd.webmovies.media.movie.MyMovie;
import xd.webmovies.media.television.MyTVShow;
import xd.webmovies.security.login.LoginForm;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> showUsers(){
       return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getOneUser(id);
    }

    @GetMapping("/user/tvshows/activity")
    public Set<MyTVShow> getUserShows(Principal principal){
        return userService.getUserShows(principal.getName());
    }

    @GetMapping("/user/movies/activity")
    public Set<MyMovie> getUserMovies(Principal principal){
        return userService.getUserMovies(principal.getName());
    }

    @PostMapping("/user/add")
    public void  addUser(@RequestBody LoginForm loginForm){
        userService.addUser(loginForm);
    }
}
