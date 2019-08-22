package xd.webmovies.media.television;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MyTVShowControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    MyTVShowService myTVShowService;

    private List<MyTVShow> myTVShowList = new ArrayList<>();

    @BeforeAll
    private void setup(){
        MyTVShow myTVShow1 = new MyTVShow(9,"Test Comment1","Test status1");
        MyTVShow myTVShow2 = new MyTVShow(8,"Test Comment2","Test status2");
        MyTVShow myTVShow3 = new MyTVShow(7,"Test Comment3","Test status3");

        myTVShow1.setId(1L);
        myTVShow2.setId(2L);
        myTVShow3.setId(3L);

        myTVShowList.add(myTVShow1);
        myTVShowList.add(myTVShow2);
        myTVShowList.add(myTVShow3);

    }

    @Test
    @WithMockUser(username = "TestName",roles = "ADMIN")
    void getAllShows() throws Exception{

        when(myTVShowService.getAll()).thenReturn(myTVShowList);

        mvc.perform(MockMvcRequestBuilders
                .get("/mytvshows")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].comment").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].comment").value("Test Comment1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].status").value("Test status2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].rate").value(7));


    }

    @Test
    void getLatestShows() throws Exception {
        List<MyDTO> myDTOList = new ArrayList<>();
        myDTOList.add(new MyDTO("Test Comment1","Test status1",2));
        myDTOList.add(new MyDTO("Test Comment2","Test status2",3));
        myDTOList.add(new MyDTO("Test Comment3","Test status3",4));

        when(myTVShowService.getLatestShows()).thenReturn(myDTOList);

        mvc.perform(MockMvcRequestBuilders
        .get("/mytvshows/latest")
        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].comment").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].status").value("Test status1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].rating").value(3));

    }

    @Test
    @WithMockUser(username = "TestName", roles = "ADMIN")
    void addShow() throws Exception{

        MyDTO myDTO = new MyDTO("Test Comment","Test status",11);

        mvc.perform(MockMvcRequestBuilders
        .post("/mytvshows/{id}",1L)
        .content(new ObjectMapper().writeValueAsString(myDTO))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Added"));

        verify(myTVShowService,times(1)).saveMyShow(anyLong(),any(MyDTO.class),any(Principal.class));

    }

    @Test
    @WithMockUser(username = "TestName", roles = "ADMIN")
    void deleteShow() throws Exception{

        when(myTVShowService.getOne(1L)).thenReturn(myTVShowList.get(0));

        mvc.perform(MockMvcRequestBuilders
        .delete("/mytvshows/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));

        verify(myTVShowService,times(1)).deleteMyShow(1L);
    }
}