package xd.webmovies.security.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xd.webmovies.security.JwtTokenUtil;



@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    private JwtTokenUtil jwtTokenUtil;
    private AuthenticationManager authenticationManager;

    public LoginController(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public LoginResponse hello(@RequestBody LoginForm login) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenUtil.generateToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new LoginResponse(jwt,userDetails.getUsername(), userDetails.getAuthorities());
    }
}
