package xd.webmovies.media.movie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class MovieServiceTest {



    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieService movieService;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getOne() {
        Movie movie1 = new Movie();
        movie1.setDescription("asdasdas");
        movie1.setId(12L);
        movie1.setTitle("Avengers");
        movie1.setYear(1221);

        when(movieRepository.findById(12L)).thenReturn(Optional.of(movie1));

        Movie movieFound = movieService.getOne(12L);

        assertEquals(movieFound.getTitle(),movie1.getTitle());
    }

    @Test
    public void getAll(){
        Movie movie1 = new Movie("Venom",2011,"sadasd","asdasdasd");
        Movie movie2 = new Movie("Avengers",2011,"dasfasd","saasdasd");
        List<Movie> moviesList = new ArrayList<>();
        moviesList.add(movie1);
        moviesList.add(movie2);

        when(movieRepository.findAll()).thenReturn(moviesList);

        List<Movie> result = movieService.showAllMovies();

        assertEquals(2,result.size());
    }


    @Test
    public void deleteMovie(){
        Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("Venom");

        movieService.deleteMovie(1L);

        verify(movieRepository,times(1)).deleteById(1L);
    }
}