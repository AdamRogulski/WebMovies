package xd.webmovies.media.television;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class TVShow{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    @Size(max = 75)
    private String title;

    @PositiveOrZero
    private int year;

    @Lob
    private String description;

    @PositiveOrZero
    private int episodes;

    private String image;

    @OneToMany(mappedBy = "tvShow")
    @JsonBackReference
    private Set<MyTVShow> myShows;

    private LocalDateTime showAddedTime = LocalDateTime.now();

    public TVShow() {
    }

    public TVShow(@NotEmpty @Size(max = 75) String title, @PositiveOrZero int year, String description, @PositiveOrZero int episodes, String image) {
        this.title = title;
        this.year = year;
        this.description = description;
        this.episodes = episodes;
        this.image = image;
    }



    @JsonFormat(pattern = "HH:mm dd.MM.yyyy")
    public LocalDateTime getShowAddedTime() {
        return showAddedTime;
    }

    public Set<MyTVShow> getMyShows() {
        return myShows;
    }

    public void setMyShows(Set<MyTVShow> myShows) {
        this.myShows = myShows;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
