package xd.webmovies.user;


import com.fasterxml.jackson.annotation.*;
import xd.webmovies.media.movie.MyMovie;
import xd.webmovies.media.television.MyTVShow;
import xd.webmovies.user.authority.Authority;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 6)
    @Column(unique = true)
    private String username;

    @Size(min = 6)
    private String password;

    @AssertTrue
    private boolean isActive;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "User_Authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> user_Authority;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<MyTVShow> myShows;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<MyMovie> myMovies;

    public User() {
    }

    public User(@Size(min = 6) String username, @Size(min = 6) String password, @AssertTrue boolean isActive) {
        this.username = username;
        this.password = password;
        this.isActive = isActive;
    }

    public Set<MyMovie> getMyMovies() {
        return myMovies;
    }

    public void setMyMovies(Set<MyMovie> myMovies) {
        this.myMovies = myMovies;
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

    public String getUsername() {
        return username;
    }

    @JsonProperty
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<Authority> getUser_Authority() {
        return user_Authority;
    }

    public void setUser_Authority(Set<Authority> user_Authority) {
        this.user_Authority = user_Authority;
    }
}

