package xd.webmovies.media.television;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class TVShowService {

    private TVShowRepository tvShowRepository;

    public TVShowService(TVShowRepository tvShowRepository) {
        this.tvShowRepository = tvShowRepository;
    }

    List<TVShow> getAllShows(){
       return tvShowRepository.findAll();
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

    TVShow getOneTVShow(Long id){
       return tvShowRepository.findById(id).orElse(null);
    }

    void deleteTVShow(Long id){
        tvShowRepository.deleteById(id);
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
