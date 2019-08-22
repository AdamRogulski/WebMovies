package xd.webmovies.media.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xd.webmovies.media.MyDTO;
import xd.webmovies.media.television.MyTVShow;
import xd.webmovies.user.User;
import xd.webmovies.user.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MyMovieService {

    @Autowired
   private MovieService movieService;

    @Autowired
   private UserService userService;

    @Autowired
   private MyMovieRepository myMovieRepository;

    List<MyMovie> getAll(){
        return myMovieRepository.findAll();
    }

    List<MyDTO> getLatest8Posts(){

        List<MyMovie> myMovieList = myMovieRepository.findTop8ByOrderByCreationTimeDesc();
        List<MyDTO> myMovieDTOList = new ArrayList<>();

        for(MyMovie m : myMovieList){
            MyDTO myMovieDTO = new MyDTO();
            myMovieDTO.setStatus(m.getStatus());
            myMovieDTO.setComment(m.getComment());
            myMovieDTO.setRating(m.getRate());
            myMovieDTO.setCreationTime(m.getCreationTime());
            myMovieDTO.setAuthor(m.getUser().getUsername());
            myMovieDTO.setTitle(m.getMovie().getTitle());
            myMovieDTOList.add(myMovieDTO);
        }
        return myMovieDTOList;
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

    void deleteMyMovieByMovieId(Long id){
        List<MyMovie> myMovieList = myMovieRepository.findAllByMovie_Id(id);

        for (MyMovie x : myMovieList){
            myMovieRepository.delete(x);
        }
    }

    MyMovie getOne(Long id){
        return myMovieRepository.findById(id).orElse(null);
    }
}
