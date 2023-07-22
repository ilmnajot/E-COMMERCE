package uz.ilmnajot.registration.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private final long expireTime = 1 * 24 * 3600 * 1000L; // one day

    private final String secretKey  = "mySecretKeyIsHere";
    public String generateToken(String username) {
       return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
// jwt class is ready
}
