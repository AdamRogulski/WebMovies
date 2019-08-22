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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xd.webmovies.media.MediaDTO;
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
public class MovieControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    MovieService movieService;

    private List<Movie> movieList = new ArrayList<>();

    @BeforeAll
    void setup(){
        Movie movie1 = new Movie("TitleTest1",2010,"Test Description1","Test image1");
        Movie movie2 = new Movie("TitleTest2",2011,"Test Description2","Test image2");
        Movie movie3 = new Movie("TitleTest3",2012,"Test Description3","Test image3");

        movie1.setId(1L);
        movie2.setId(2L);
        movie3.setId(3L);

        movieList.add(movie1);
        movieList.add(movie2);
        movieList.add(movie3);
    }


    @Test
    void getMovies() throws Exception {

        when(movieService.showAllMovies()).thenReturn(movieList);

        mvc.perform(MockMvcRequestBuilders
                .get("/movies")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].title").isNotEmpty());
    }

    @Test
    void getOneMovie() throws Exception {

        when(movieService.getOne(1L)).thenReturn(movieList.get(0));

        mvc.perform(MockMvcRequestBuilders
                .get("/movies/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void getComments() throws Exception {

        MyDTO myMovieDTO = new MyDTO();
        myMovieDTO.setRating(9);
        myMovieDTO.setComment("Test Comments");
        myMovieDTO.setStatus("TestStatus");

        Set<MyDTO> myMovieList = new HashSet<>();
        myMovieList.add(myMovieDTO);

        when(movieService.getComments(1L)).thenReturn(myMovieList);

        mvc.perform(MockMvcRequestBuilders
                .get("/movies/{id}/comments", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].comment").exists());
    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void addMovie() throws Exception {

                mvc.perform(MockMvcRequestBuilders
                .post("/movies/add")
                .content(asJsonString(new MediaDTO("Test image","Test Description","Test Title",2011)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("Movie added"));

                verify(movieService,times(1)).addMovie(any(MediaDTO.class));
    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void addMovieWithNotUniqueTitle() throws Exception {

        when(movieService.isTitleNotUnique(anyString())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders
                .post("/movies/add")
                .content(asJsonString(new MediaDTO("Test image","Test Description","TestTitle1",2011)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Movie with this title is already in database"));

    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void deleteMovie() throws Exception{

        when(movieService.getOne(anyLong())).thenReturn(movieList.get(0));

        mvc.perform(MockMvcRequestBuilders
                .delete("/movies/{id}",1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Movie with id: 1 deleted"));

        verify(movieService,times(1)).deleteMovie(1L);
    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void deleteMovieWithNegativeNumber() throws Exception{

        Movie movie = new Movie();
        movie.setId(-1L);
        when(movieService.getOne(-1L)).thenReturn(movie);

        mvc.perform(MockMvcRequestBuilders
                .delete("/movies/{id}",-1))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Id must be positive number"));
    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void deleteMovieWithNotExistingId() throws Exception{

        mvc.perform(MockMvcRequestBuilders
                .delete("/movies/{id}",1))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Can't find movie with id: 1"));
    }


    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void updateMovie() throws Exception{

        mvc.perform(MockMvcRequestBuilders
        .put("/movies/{id}",1)
        .content(asJsonString(new MediaDTO("Test image","Test Description","TestTitle",2011)))
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization","Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwc3plbWVrIn0.ZRuDUuElWrVcaatU4dyf9TBh1qMeGXT_8szsfNxS8zYJ9_85j_gQDOKJh5D3G97UIn59WJll1jivXt8lZW1dPw"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Movie updated"));

        verify(movieService,times(1)).updateMovie(anyLong(),any(MediaDTO.class));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}