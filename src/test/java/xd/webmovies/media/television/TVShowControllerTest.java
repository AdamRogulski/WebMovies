package xd.webmovies.media.television;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xd.webmovies.media.MyDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class TVShowControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TVShowService tvShowService;

    private List<TVShow> testList = new ArrayList<>();

    @BeforeAll
     void setup(){
        TVShow tvShow1 = new TVShow("TestTitle1",2011,"Test description",12,"Test image");
        TVShow tvShow2 = new TVShow("TestTitle2",2011,"Test description",12,"Test image");
        TVShow tvShow3 = new TVShow("TestTitle3",2011,"Test description",12,"Test image");

        tvShow1.setId(1L);
        tvShow2.setId(2L);
        tvShow3.setId(3L);

        testList.add(tvShow1);
        testList.add(tvShow2);
        testList.add(tvShow3);
    }

    @Test
    void getAll() throws Exception{

        when(tvShowService.getAllShows()).thenReturn(testList);

        mvc.perform(MockMvcRequestBuilders
        .get("/tvshows")
        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value("TestTitle1"));
    }

    @Test
    void getOneShow() throws Exception{

        when(tvShowService.getOneTVShow(1L)).thenReturn(testList.get(0));

        mvc.perform(MockMvcRequestBuilders
        .get("/tvshows/{id}",1)
        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("TestTitle1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").exists());
    }

    @Test
    void getComments() throws Exception{

        MyDTO myTVShow = new MyDTO();
        myTVShow.setComment("Test comment");
        myTVShow.setStatus("test");
        myTVShow.setRating(12);

        Set<MyDTO> myTVShows = new HashSet<>();
        myTVShows.add(myTVShow);

        when(tvShowService.getComments(1L)).thenReturn(myTVShows);

        mvc.perform(MockMvcRequestBuilders
                .get("/tvshows/{id}/comments",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].comment").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void addShow() throws Exception{

        mvc.perform(MockMvcRequestBuilders
        .post("/tvshows/add")
        .content(asJsonString(new TVShowDTO("TestTitle",2011,"Test Desciption",12,"Test image")))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("TV Show added"));

        verify(tvShowService,times(1)).addShow(any(TVShowDTO.class));
    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void addShowWithNotUniqueTitle() throws Exception{

        when(tvShowService.isTitleNotUnique(anyString())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders
                .post("/tvshows/add")
                .content(asJsonString(new TVShowDTO("TestTitle",2011,"Test Desciption",12,"Test image")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string("TV Show with this title is already in database"));

    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void updateShow() throws Exception{

        when(tvShowService.getOneTVShow(anyLong())).thenReturn(testList.get(0));

        mvc.perform(MockMvcRequestBuilders
                .put("/tvshows/{id}",0)
                .content(asJsonString(new TVShowDTO("UpdatedTitle",2011,"UpdatedDescription",22,"Image")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("TV show updated"));

        verify(tvShowService,times(1)).updateShow(anyLong(),any(TVShowDTO.class));

    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void updateShowWithNotExistingId() throws Exception{

        mvc.perform(MockMvcRequestBuilders
                .put("/tvshows/{id}",0)
                .content(asJsonString(new TVShowDTO("UpdatedTitle",2011,"UpdatedDescription",22,"Image")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Can't find TV Show with this id to update"));

    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void deleteShow() throws Exception{

        when(tvShowService.getOneTVShow(1L)).thenReturn(testList.get(0));

        mvc.perform(MockMvcRequestBuilders
                .delete("/tvshows/{id}",1))
                .andExpect(status().isOk());

        verify(tvShowService,times(1)).deleteTVShow(1L);
    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void deleteShowWithNotExistingId() throws Exception{


        mvc.perform(MockMvcRequestBuilders
                .delete("/tvshows/{id}",1))
                .andExpect(status().isNotFound());

    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}