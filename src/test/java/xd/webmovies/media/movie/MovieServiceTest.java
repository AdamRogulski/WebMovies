package xd.webmovies.media.movie;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xd.webmovies.media.MediaDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieServiceTest {

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieService movieService;

    private List<Movie> moviesList = new ArrayList<>();

    @BeforeAll
    void setup(){
        MockitoAnnotations.initMocks(this);

        Movie movie1 = new Movie("TestTitle1",2011,"TestComment1","TestImage1");
        Movie movie2 = new Movie("TestTitle2",2010,"TestComment2","TestImage2");
        Movie movie3 = new Movie("TestTitle3",2012,"TestComment3","TestImage3");


        moviesList.add(movie1);
        moviesList.add(movie2);
        moviesList.add(movie3);
    }

    @Test
    void getOne() {
        Movie movie1 = moviesList.get(0);

        when(movieRepository.findById(12L)).thenReturn(Optional.of(movie1));

        Movie movieFound = movieService.getOne(12L);

        assertEquals(movieFound.getTitle(),movie1.getTitle());
    }

    @Test
    void getAll(){
        when(movieRepository.findAll()).thenReturn(moviesList);

        List<Movie> result = movieService.showAllMovies();

        assertEquals(3,result.size());
    }


    @Test
    void deleteMovie(){
        Movie movie1 = moviesList.get(0);

        when(movieRepository.getOne(1L)).thenReturn(movie1);

        movieService.deleteMovie(1L);

        verify(movieRepository,times(1)).deleteById(1L);
    }

    @Test
    void updateMovie(){
        Movie movie1 = moviesList.get(0);
        movie1.setId(1L);

        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setTitle("Title After Update");
        mediaDTO.setYear(2019);
        mediaDTO.setDescription("This is description after update");
        mediaDTO.setImage("Image after Update");

        when(movieRepository.getOne(movie1.getId())).thenReturn(movie1);

        movieService.updateMovie(1L,mediaDTO);

        assertEquals(movie1.getTitle(),"Title After Update");
        assertEquals(movie1.getYear(),2019);
    }

}