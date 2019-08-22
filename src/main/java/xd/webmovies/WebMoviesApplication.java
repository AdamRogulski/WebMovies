package xd.webmovies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication
public class WebMoviesApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebMoviesApplication.class, args);
    }

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Warsaw"));
    }
}
