package xd.webmovies.user;


import xd.webmovies.user.authority.Authority;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 6)
    private String username;

    @Size(min = 6)
    private String password;

    @AssertTrue
    boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "User_Authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> user_Authority;

    public User() {
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

    public void setUsername(String username) {
        this.username = username;
    }

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

