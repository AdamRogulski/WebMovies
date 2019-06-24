package xd.webmovies.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    void addUser(User user){
        userRepository.save(user);
    }
}
