package xd.webmovies.media.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xd.webmovies.media.MediaDTO;
import xd.webmovies.media.MyDTO;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class MovieService {

    @Autowired
    private MyMovieService myMovieService;

    @Autowired
    private MovieRepository movieRepository;


    List<Movie> showAllMovies(){
       return movieRepository.findAll();
    }

    List<Movie> getLatest10Movies(){
        return movieRepository.findTop10ByOrderByMovieAddedTimeDesc();
    }

    List<MediaDTO> searchMovieByTitle(String title){
        List<Movie> movieList = movieRepository.findAllByTitleContains(title);
        List<MediaDTO> mediaDTOSList = new ArrayList<>();
        for (Movie m :movieList){
            MediaDTO mediaDTO = new MediaDTO();
            mediaDTO.setId(m.getId());
            mediaDTO.setTitle(m.getTitle());
            mediaDTO.setDescription(m.getDescription());
            mediaDTO.setImage(m.getImage());
            mediaDTO.setYear(m.getYear());
            mediaDTOSList.add(mediaDTO);
        }

        return mediaDTOSList;
    }

    void addMovie(MediaDTO movieDTO){

        Movie movie = new Movie();

        movie.setImage(movieDTO.getImage());
        movie.setDescription(movieDTO.getDescription());
        movie.setYear(movieDTO.getYear());
        movie.setTitle(movieDTO.getTitle());

            if (movie.getDescription()==null)
                movie.setDescription("Movie hasn't description yet");

            if(movie.getYear() < 0){
                movie.setYear(0);
                System.out.println("Year must be positive number, setting year to default number 0");
            }
            if(movie.getYear()<1800 || movie.getYear()>2100 ){
                movie.setYear(0);
                System.out.println("Year must have value between 1800 and 2100, setting year to default number 0");
            }

            movieRepository.save(movie);

        }

    Set<MyDTO>  getComments(Long id){
        Movie movie = movieRepository.getOne(id);

        Set<MyDTO> myMovieDTOS = new LinkedHashSet<>();

        for(MyMovie m : movie.getMyMovie()) {
            MyDTO myMovieDTO = new MyDTO();
            myMovieDTO.setComment(m.getComment());
            myMovieDTO.setAuthor(m.getUser().getUsername());
            myMovieDTO.setRating(m.getRate());
            myMovieDTO.setCreationTime(m.getCreationTime());
            myMovieDTOS.add(myMovieDTO);
        }

        return myMovieDTOS;
    }

    void deleteMovie(Long id){

        if (movieRepository.getOne(id).getMyMovie() != null){
            myMovieService.deleteMyMovieByMovieId(id);
        }
        movieRepository.deleteById(id);
    }

    boolean isTitleNotUnique(String title){
        return movieRepository.existsByTitle(title);
    }

    Movie getOne(Long id){
        return movieRepository.findById(id).orElse(null);
    }

    void updateMovie(Long id,MediaDTO movieDTO){

        Movie oldMovie = movieRepository.getOne(id);

        oldMovie.setTitle(movieDTO.getTitle());
        oldMovie.setYear(movieDTO.getYear());
        oldMovie.setDescription(movieDTO.getDescription());
        oldMovie.setImage(movieDTO.getImage());

        movieRepository.save(oldMovie);
    }
}
