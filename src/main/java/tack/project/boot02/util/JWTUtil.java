package tack.project.boot02.util;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Component
public class JWTUtil {

    ///////////////////////////////////////////////////////////////////////////////////////
    public static class CustomJWTException extends RuntimeException {

        public CustomJWTException(String msg) {
            super(msg);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    @Value("${tack.project.jwt.secret}")
    private String key;

    ///////////////////////////////////////////////////////////////////////////////////////
    public String generate(Map<String, Object> claimMap, int min) {

        // 헤더 만들기.
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");

        // claims 만들기.
        Map<String, Object>claims = new HashMap<>();
        claims.putAll(claimMap);

        SecretKey key = null;
        try {
            key = Keys.hmacShaKeyFor(this.key.getBytes(StandardCharsets.UTF_8));
        } catch(Exception e) {

        }
        String jwtStr = Jwts.builder()
            .setHeader(headers)
            .setClaims(claims)
            .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
            .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
            .signWith(key)
            .compact();

        return  jwtStr;

    }


    public Map<String, Object> validateToken(String token) {

        Map<String, Object> claims = null;

        if(token == null) {
            throw new CustomJWTException("NullToken");
        }

        try {
            SecretKey key = Keys.hmacShaKeyFor(this.key.getBytes(StandardCharsets.UTF_8));

            claims = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody();

        } catch(MalformedJwtException e) {
            throw new CustomJWTException("MalFormed");
        } catch (ExpiredJwtException e) {
            throw new CustomJWTException("Expired");
        } catch(InvalidClaimException e) {
            throw new CustomJWTException("Invalid");
        } catch(JwtException e) {
            throw new CustomJWTException(e.getMessage());
        } catch(Exception e) {
            throw new CustomJWTException("Error");
        }

        return claims;

    }
    
}
