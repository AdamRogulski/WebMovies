package xd.webmovies.media.television;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import xd.webmovies.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MyTVShow{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_id")
    @JsonManagedReference
    private TVShow tvShow;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    private int rate;

    @Lob
    private String comment;

    private String status;

    private LocalDateTime creationTime = LocalDateTime.now();

    public MyTVShow() {
    }

    public MyTVShow(int rate, String comment, String status) {
        this.rate = rate;
        this.comment = comment;
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

    public TVShow getTvShow() {
        return tvShow;
    }

    public void setTvShow(TVShow tvShow) {
        this.tvShow = tvShow;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
