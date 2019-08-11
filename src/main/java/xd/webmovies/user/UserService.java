package xd.webmovies.user;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import xd.webmovies.media.movie.MyMovie;
import xd.webmovies.media.television.MyTVShow;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    User getOneUser(Long id){
        return userRepository.getOne(id);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    void addUser(User user){
        userRepository.save(user);
    }

    Set<MyTVShow> getUserShows(String username){
        User user = userRepository.findByUsername(username);
        return user.getMyShows();
    }

    Set<MyMovie> getUserMovies(String username){
        User user = userRepository.findByUsername(username);
        return user.getMyMovies();
    }
}
