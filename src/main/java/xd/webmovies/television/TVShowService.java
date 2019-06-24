package xd.webmovies.television;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TVShowService {

    private TVShowRepository tvShowRepository;

    public TVShowService(TVShowRepository tvShowRepository) {
        this.tvShowRepository = tvShowRepository;
    }

    List<TVShow> getAllShows(){
       return tvShowRepository.findAll();
    }

    void addShow(TVShow tvShow){
        tvShowRepository.save(tvShow);
    }

    TVShow getOneTVShow(Long id){
       return tvShowRepository.findById(id).orElse(null);
    }

    void deleteTVShow(Long id){
        tvShowRepository.deleteById(id);
    }

    boolean isTVShowTitleNotUnique(String title){
        return tvShowRepository.existsByTitle(title);
    }
}
