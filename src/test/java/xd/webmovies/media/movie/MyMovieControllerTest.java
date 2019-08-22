package xd.webmovies.media.movie;

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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyMovieControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    MyMovieService myMovieService;

    private List<MyMovie> myMovieList = new ArrayList<>();

    @BeforeAll
    void setup() {
        MyMovie myMovie1 = new MyMovie("Test Comment", 9, "TestStatus");
        MyMovie myMovie2 = new MyMovie("Test Comment", 9, "TestStatus");
        MyMovie myMovie3 = new MyMovie("Test Comment", 9, "TestStatus");

        myMovie1.setId(1L);
        myMovie2.setId(2L);
        myMovie3.setId(3L);

        myMovieList.add(myMovie1);
        myMovieList.add(myMovie2);
        myMovieList.add(myMovie3);
    }

    @Test
    @WithMockUser(username = "TestName", roles = "ADMIN")
    void getAllMyMovies() throws Exception {

        when(myMovieService.getAll()).thenReturn(myMovieList);

        mvc.perform(MockMvcRequestBuilders
                .get("/mymovies")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].comment").isNotEmpty());

    }

    @Test
    @WithMockUser(username = "TestName", roles = "ADMIN")
    void addMyMovie() throws Exception {


        mvc.perform(MockMvcRequestBuilders
                .post("/mymovies/{id}",1)
                .content(asJsonString(new MyDTO("TestComment1","TestStatus1",10)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Post added"));

        verify(myMovieService,times(1)).saveMyMovie(anyLong(),any(MyDTO.class),any(Principal.class));

    }

    @Test
    @WithMockUser(username = "TestName", roles = "ADMIN")
    void deleteMyMovie() throws Exception{

        when(myMovieService.getOne(1L)).thenReturn(myMovieList.get(0));

        mvc.perform(MockMvcRequestBuilders
                .delete("/mymovies/{id}",1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Post deleted"));

        verify(myMovieService,times(1)).deleteMyMovie(1L);

    }

    @Test
    @WithMockUser(username = "TestName", roles = "ADMIN")
    void deleteMyMovieWithNotExistiongId() throws Exception{


        mvc.perform(MockMvcRequestBuilders
                .delete("/mymovies/{id}",1))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Can't find movie with this id"));


    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}