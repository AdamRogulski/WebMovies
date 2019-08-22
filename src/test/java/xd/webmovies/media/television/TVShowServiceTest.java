package xd.webmovies.media.television;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TVShowServiceTest {

    @Mock
    TVShowRepository tvShowRepository;

    @InjectMocks
    TVShowService tvShowService;

    private List<TVShow> showsList = new ArrayList<>();

    @BeforeAll
    void setup(){
        MockitoAnnotations.initMocks(this);

        TVShow tvShow1 = new TVShow("TestTitle1",2011,"Test description1",12,"Test image");
        TVShow tvShow2 = new TVShow("TestTitle2",2008,"Test description2",6,"Test image");
        TVShow tvShow3 = new TVShow("TestTitle3",1999,"Test description3",19,"Test image");

        showsList.add(tvShow1);
        showsList.add(tvShow2);
        showsList.add(tvShow3);
    }

    @Test
    void getAllShows() {


        when(tvShowRepository.findAll()).thenReturn(showsList);

        List<TVShow> result = tvShowService.getAllShows();

        assertEquals(result.size(),3);
    }

    @Test
    void addShow() {
        TVShowDTO tvShowDTO1 = new TVShowDTO();
        tvShowDTO1.setTitle("TestTitle1");
        tvShowDTO1.setEpisodes(12);
        tvShowDTO1.setDescription("Test description1");

        tvShowService.addShow(tvShowDTO1);

        verify(tvShowRepository,times(1)).save(any(TVShow.class));


    }

    @Test
    void getComments() {
        TVShow tvShow = showsList.get(0);

        MyTVShow myTVShow = new MyTVShow();
        myTVShow.setComment("Test comment");
        myTVShow.setStatus("test");
        myTVShow.setRate(12);

        Set<MyTVShow> myTVShows = new HashSet<>();
        myTVShows.add(myTVShow);
        tvShow.setMyShows(myTVShows);

        when(tvShowRepository.getOne(tvShow.getId())).thenReturn(tvShow);

        assertEquals(tvShow.getMyShows(),myTVShows);


    }

    @Test
    void getOneTVShow() {
        TVShow tvShow = showsList.get(0);

        when(tvShowRepository.findById(anyLong())).thenReturn(Optional.of(tvShow));

        TVShow receivedShow = tvShowService.getOneTVShow(1L);

        assertEquals("TestTitle1",receivedShow.getTitle());
        assertEquals(2011, receivedShow.getYear());
    }

    @Test
    void deleteTVShow() {
        TVShow tvShow = showsList.get(0);
        when(tvShowRepository.getOne(1L)).thenReturn(tvShow);
        tvShowService.deleteTVShow(1L);
        verify(tvShowRepository,times(1)).deleteById(1L);
    }

    @Test
    void updateShow() {

        TVShow tvShow = showsList.get(1);
        tvShow.setId(1L);

        TVShowDTO tvShowDTO = new TVShowDTO();
        tvShowDTO.setTitle("TitleAfterUpdate");
        tvShowDTO.setDescription("Description after update");
        tvShowDTO.setYear(2011);
        tvShowDTO.setEpisodes(7);

        when(tvShowRepository.getOne(1L)).thenReturn(tvShow);

        tvShowService.updateShow(1L,tvShowDTO);

        assertEquals(tvShow.getTitle(),"TitleAfterUpdate");
        assertEquals(tvShow.getEpisodes(),7);


    }
}