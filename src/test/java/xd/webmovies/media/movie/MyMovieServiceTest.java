package xd.webmovies.media.movie;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xd.webmovies.media.MyDTO;
import xd.webmovies.user.User;
import xd.webmovies.user.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyMovieServiceTest {

    @Mock
    MyMovieRepository myMovieRepository;

    @Mock
    UserService userService;

    @Mock
    MovieService movieService;

    @InjectMocks
    MyMovieService myMovieService;

    private List<MyMovie> myMovieList = new ArrayList<>();

    @BeforeAll
    void setup(){
        MockitoAnnotations.initMocks(this);

        MyMovie myMovie1 = new MyMovie("TestComment1",9,"Test Status1");
        MyMovie myMovie2 = new MyMovie("TestComment2",8,"Test Status2");
        MyMovie myMovie3 = new MyMovie("TestComment3",7,"Test Status3");

        myMovieList.add(myMovie1);
        myMovieList.add(myMovie2);
        myMovieList.add(myMovie3);
    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void getAll() {

        when(myMovieRepository.findAll()).thenReturn(myMovieList);

       List<MyMovie> result = myMovieService.getAll();

        assertEquals(result.size(),3);
    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void saveMyMovie() {
        MyDTO myDTO = new MyDTO("TestComment","Test Status",1);
        User user = new User("TestUsername","testpassword",true);
        Movie movie = new Movie("TestTitle",2010,"Test description","Test image");

        Principal principal = user::getUsername;

        when(userService.getUserByUsername("TestUsername")).thenReturn(user);
        when(movieService.getOne(1L)).thenReturn(movie);

        myMovieService.saveMyMovie(1L,myDTO,principal);

        verify(myMovieRepository,times(1)).save(any(MyMovie.class));


    }

    @Test
    void deleteMyMovie() {
        MyMovie myMovie1 = myMovieList.get(0);
        myMovie1.setId(1L);
        myMovieService.deleteMyMovie(1L);

        verify(myMovieRepository,times(1)).deleteById(1L);
    }

    @Test
    void getOne() {
        MyMovie myMovie1 = myMovieList.get(0);

        when(myMovieRepository.findById(1L)).thenReturn(Optional.of(myMovie1));

        MyMovie result = myMovieService.getOne(1L);

        assertEquals(result.getComment(),"TestComment1");
        assertEquals(result.getRate(),9);

    }
}