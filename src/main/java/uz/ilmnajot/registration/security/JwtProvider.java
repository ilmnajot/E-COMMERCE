package uz.ilmnajot.registration.security;

import io.jsonwebtoken.*;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private final static long  expireTime = 1 * 24 * 3600 * 1000L; // one day

    private final static String secretKey = "mySecretKeyIsHere";

    public  static synchronized String generateToken(String username) {
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
             Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
