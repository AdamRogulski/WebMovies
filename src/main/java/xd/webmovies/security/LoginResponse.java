package xd.webmovies.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

public class LoginResponse {
    private String token;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;


    public LoginResponse(String token, String username, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.username = username;
        this.authorities = authorities;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
