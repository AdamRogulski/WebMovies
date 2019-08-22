package xd.webmovies.user;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xd.webmovies.media.movie.MyMovie;
import xd.webmovies.media.television.MyTVShow;
import xd.webmovies.security.login.LoginForm;
import xd.webmovies.user.authority.Authority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static xd.webmovies.user.authority.AuthorityType.ROLE_USER;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    User getOneUser(Long id){
        return userRepository.getOne(id);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    void addUser(LoginForm loginForm){
        User user = new User();
        user.setPassword(encoder.encode(loginForm.getPassword()));
        user.setUsername(loginForm.getUsername());
        user.setActive(true);
        Set<Authority> authoritySet = new HashSet<>();
        authoritySet.add(new Authority(ROLE_USER));
        user.setUser_Authority(authoritySet);
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
