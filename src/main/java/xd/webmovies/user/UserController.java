package xd.webmovies.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> showUsers(){
       return userService.getAllUsers();
    }

    @PostMapping("/user/add")
    public void  addUser(@RequestBody User user){
        userService.addUser(user);
    }
}
