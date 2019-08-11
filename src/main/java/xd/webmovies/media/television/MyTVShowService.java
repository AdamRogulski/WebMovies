package xd.webmovies.media.television;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xd.webmovies.media.MyDTO;
import xd.webmovies.user.User;
import xd.webmovies.user.UserService;

import java.security.Principal;
import java.util.List;

@Service
public class MyTVShowService {

    @Autowired
    MyTVShowRepository myTVShowRepository;

    @Autowired
    UserService userService;

    @Autowired
    TVShowService tvShowService;

    List<MyTVShow> getAll(){
        return myTVShowRepository.findAll();
    }

    MyTVShow getOne(Long id){
        return myTVShowRepository.getOne(id);
    }

    void saveMyShow(Long id, MyDTO myDTO, Principal principal){

        MyTVShow myTVShow = new MyTVShow();
        myTVShow.setComment(myDTO.getComment());
        myTVShow.setRate(myDTO.getRating());
        myTVShow.setStatus(myDTO.getStatus());

        User user = userService.getUserByUsername(principal.getName());
        myTVShow.setUser(user);

        TVShow tvShow = tvShowService.getOneTVShow(id);
        myTVShow.setTvShow(tvShow);

        myTVShowRepository.save(myTVShow);
    }

    void deleteMyShow(Long id){
        myTVShowRepository.deleteById(id);
    }
}
