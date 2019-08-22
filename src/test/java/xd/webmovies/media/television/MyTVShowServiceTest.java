package xd.webmovies.media.television;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MyTVShowServiceTest {

    @Mock
    private MyTVShowRepository myTVShowRepository;

    @Mock
    private UserService userService;

    @Mock
    private TVShowService tvShowService;

    @InjectMocks
    private MyTVShowService myTVShowService;

    private List<MyTVShow> testList = new ArrayList<>();

    @BeforeAll
    void setup(){
        MockitoAnnotations.initMocks(this);

        MyTVShow myTVShow1 = new MyTVShow(12,"testComment1","testStatus1");
        MyTVShow myTVShow2 = new MyTVShow(17,"testComment2","testStatus2");
        MyTVShow myTVShow3 = new MyTVShow(5,"testComment3","testStatus3");

        User user1 = new User("TestUsername","testpassword",true);
        TVShow tvShow1 = new TVShow("TestTitle1",2010,"TestDescription1",10,"TestImage");

        myTVShow1.setTvShow(tvShow1);
        myTVShow1.setUser(user1);

        myTVShow2.setTvShow(tvShow1);
        myTVShow2.setUser(user1);

        myTVShow3.setTvShow(tvShow1);
        myTVShow3.setUser(user1);

        testList.add(myTVShow1);
        testList.add(myTVShow2);
        testList.add(myTVShow3);
    }

    @Test
    void getAll() {

        when(myTVShowRepository.findAll()).thenReturn(testList);

        List<MyTVShow> result = myTVShowService.getAll();

        assertEquals(result.size(),3);
        assertEquals(result.get(0).getComment(),"testComment1");

    }

    @Test
    void getLatestShows() {

        when(myTVShowRepository.findTop8ByOrderByCreationTimeDesc()).thenReturn(testList);

        List<MyDTO> result = myTVShowService.getLatestShows();

        assertEquals(result.size(),3);
    }

    @Test
    void getOne() {

        when(myTVShowRepository.getOne(anyLong())).thenReturn(testList.get(0));

        MyTVShow result = myTVShowService.getOne(1L);

        assertEquals(result.getComment(),"testComment1");
        assertEquals(result.getRate(),12);
    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void saveMyShow() {

        MyDTO myDTO1 = new MyDTO("TestComment1","TestStatsu1",10);
        User user1 = new User("TestUsername","testpassword",true);
        TVShow tvShow1 = new TVShow("TestTitle1",2010,"TestDescription1",10,"TestImage");

        Principal principal = user1::getUsername;

        when(userService.getUserByUsername("TestUsername")).thenReturn(user1);
        when(tvShowService.getOneTVShow(anyLong())).thenReturn(tvShow1);

        myTVShowService.saveMyShow(1L,myDTO1,principal);

        verify(myTVShowRepository,times(1)).save(any(MyTVShow.class));

    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void deleteMyShow() {
        MyTVShow myTVShow1 = testList.get(1);
        myTVShow1.setId(1L);

        myTVShowService.deleteMyShow(1L);

        verify(myTVShowRepository,times(1)).deleteById(1L);
    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void deleteMyShowsByTVShowId() {

        when(myTVShowRepository.findAllByTvShow_Id(anyLong())).thenReturn(testList);

        myTVShowService.deleteMyShowsByTVShowId(anyLong());

        verify(myTVShowRepository,times(3)).delete(any(MyTVShow.class));
    }
}