package xd.webmovies.media.television;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xd.webmovies.media.MyDTO;
import xd.webmovies.user.User;
import xd.webmovies.user.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyTVShowService {

    @Autowired
   private MyTVShowRepository myTVShowRepository;
    @Autowired
   private UserService userService;
    @Autowired
   private TVShowService tvShowService;


    List<MyTVShow> getAll(){
        return myTVShowRepository.findAll();
    }

    List<MyDTO> getLatestShows(){

        List<MyDTO> myShowsListDto = new ArrayList<>();

        List<MyTVShow>  myTVShows = myTVShowRepository.findTop8ByOrderByCreationTimeDesc();

        for (MyTVShow t : myTVShows){
            MyDTO myTVShowDTO = new MyDTO();
            myTVShowDTO.setTitle(t.getTvShow().getTitle());
            myTVShowDTO.setRating(t.getRate());
            myTVShowDTO.setAuthor(t.getUser().getUsername());
            myTVShowDTO.setComment(t.getComment());
            myTVShowDTO.setStatus(t.getStatus());
            myTVShowDTO.setCreationTime(t.getCreationTime());
            myShowsListDto.add(myTVShowDTO);
        }

        return myShowsListDto;

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

    void deleteMyShowsByTVShowId(Long id){
        List<MyTVShow> myTVShowList = myTVShowRepository.findAllByTvShow_Id(id);

        for (MyTVShow t : myTVShowList){
            myTVShowRepository.delete(t);
        }
    }
}

