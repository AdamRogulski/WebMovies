package xd.webmovies.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
public class JwtTokenUtil implements Serializable {

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey("MySecret")
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public String generateToken(Authentication authentication){

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .signWith(SignatureAlgorithm.HS512,"MySecret")
                .compact();

    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey("MySecret").parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.err.println("Invalid JWT signature" + e);
        } catch (MalformedJwtException e) {
            System.err.println("Invalid JWT token"+ e);
        }  catch (UnsupportedJwtException e) {
            System.err.println("Unsupported JWT token"+ e);
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty"+ e);
        }

        return false;
    }


}
