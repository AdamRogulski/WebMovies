package xd.webmovies.media.movie;

import org.springframework.stereotype.Service;
import xd.webmovies.media.MyDTO;
import xd.webmovies.user.User;
import xd.webmovies.user.UserService;

import java.security.Principal;
import java.util.List;

@Service
public class MyMovieService {

   private MovieService movieService;
   private UserService userService;
   private MyMovieRepository myMovieRepository;

    public MyMovieService(MovieService movieService, UserService userService, MyMovieRepository myMovieRepository) {
        this.movieService = movieService;
        this.userService = userService;
        this.myMovieRepository = myMovieRepository;
    }

    List<MyMovie> getAll(){
        return myMovieRepository.findAll();
    }

    void saveMyMovie(Long id, MyDTO myDTO, Principal principal){

        MyMovie myMovie = new MyMovie();
        myMovie.setComment(myDTO.getComment());
        myMovie.setRate(myDTO.getRating());
        myMovie.setStatus(myDTO.getStatus());

        User user = userService.getUserByUsername(principal.getName());
        myMovie.setUser(user);

        Movie movie = movieService.getOne(id);
        myMovie.setMovie(movie);

        myMovieRepository.save(myMovie);
    }

    void deleteMyMovie(Long id){
        myMovieRepository.deleteById(id);
    }

    MyMovie getOne(Long id){
        return myMovieRepository.findById(id).orElse(null);
    }
}
