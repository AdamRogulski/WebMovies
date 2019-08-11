package xd.webmovies.media.movie;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotEmpty
    @Size(max = 50)
    private String title;

    private int year;

    @Lob
    private String description;

    @Size(max = 100)
    private String image;

    @OneToMany(mappedBy = "movie")
    @JsonBackReference
    private Set<MyMovie> myMovie;

    public Set<MyMovie> getMyMovie() {
        return myMovie;
    }

    public void setMyMovie(Set<MyMovie> myMovie) {
        this.myMovie = myMovie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Movie() {
    }

    public Movie(@NotEmpty String title, int year, String description, String image) {
        this.title = title;
        this.year = year;
        this.description = description;
        this.image = image;
    }
}
