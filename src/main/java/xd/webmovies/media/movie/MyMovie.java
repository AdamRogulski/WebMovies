package xd.webmovies.media.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import xd.webmovies.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MyMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonManagedReference
    private Movie movie;


    @Lob
    private String comment;

    private int rate;
    private String status;

    private LocalDateTime creationTime = LocalDateTime.now();

    public MyMovie() {
    }

    public MyMovie(String comment, int rate, String status) {
        this.comment = comment;
        this.rate = rate;
        this.status = status;
    }

    @JsonFormat(pattern = "HH:mm dd.MM.yyyy")
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
