package xd.webmovies.media.television;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xd.webmovies.media.MediaDTO;
import xd.webmovies.media.MyDTO;

import javax.validation.constraints.AssertTrue;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TVShowService {

    @Autowired
    private MyTVShowService myTVShowService;

    @Autowired
    private TVShowRepository tvShowRepository;

    List<TVShow> getAllShows(){
       return tvShowRepository.findAll();
    }

    List<TVShow> get10LatestAddedShows(){
        return tvShowRepository.findTop10ByOrderByShowAddedTimeDesc();
    }

    List<MediaDTO> searchTVShowsByTitle(String title){
        List<TVShow> tvShowList = tvShowRepository.findAllByTitleContains(title);
        List<MediaDTO> mediaDTOSList = new ArrayList<>();

        for(TVShow tv : tvShowList){
            MediaDTO mediaDTO = new MediaDTO();
            mediaDTO.setId(tv.getId());
            mediaDTO.setYear(tv.getYear());
            mediaDTO.setImage(tv.getImage());
            mediaDTO.setDescription(tv.getDescription());
            mediaDTO.setTitle(tv.getTitle());
            mediaDTOSList.add(mediaDTO);
        }
        return mediaDTOSList;
    }
    void addShow(TVShowDTO tvShowDTO){

        TVShow tvShow1 = new TVShow();
        tvShow1.setDescription(tvShowDTO.getDescription());
        tvShow1.setEpisodes(tvShowDTO.getEpisodes());
        tvShow1.setImage(tvShowDTO.getImage());
        tvShow1.setYear(tvShowDTO.getYear());
        tvShow1.setTitle(tvShowDTO.getTitle());

        tvShowRepository.save(tvShow1);
    }

    Set<MyDTO> getComments(Long id){
        TVShow tvShow = tvShowRepository.getOne(id);

        Set<MyDTO> myDTOS = new LinkedHashSet<>();

        for(MyTVShow x : tvShow.getMyShows()){
            MyDTO myDTO1 = new MyDTO();
            myDTO1.setComment(x.getComment());
            myDTO1.setRating(x.getRate());
            myDTO1.setStatus(x.getStatus());
            myDTO1.setAuthor(x.getUser().getUsername());
            myDTO1.setCreationTime(x.getCreationTime());
            myDTO1.setTitle(x.getTvShow().getTitle());
            myDTOS.add(myDTO1);
        }

        return myDTOS;
    }

    TVShow getOneTVShow(Long id){
       return tvShowRepository.findById(id).orElse(null);
    }

    void deleteTVShow(Long id){
        if (tvShowRepository.getOne(id).getMyShows() != null)
            myTVShowService.deleteMyShowsByTVShowId(id);
        tvShowRepository.deleteById(id);
    }

    boolean isTitleNotUnique(String title){
        return tvShowRepository.existsByTitle(title);
    }

    void updateShow(Long id, TVShowDTO tvShowDTO){

        TVShow tvShow = tvShowRepository.getOne(id);
        tvShow.setEpisodes(tvShowDTO.getEpisodes());
        tvShow.setYear(tvShowDTO.getYear());
        tvShow.setImage(tvShowDTO.getImage());
        tvShow.setDescription(tvShowDTO.getDescription());
        tvShow.setTitle(tvShowDTO.getTitle());

        tvShowRepository.save(tvShow);
    }
}
