package com.altaf.DevSync.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service

public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private Long jwtExpiration = 900000L;

    public String generateToken(UserDetails user) {
        String role = user.getAuthorities()
                .stream().findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("");
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role" , role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    public SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token){
        return extractAllClaims(token).getSubject();
    }

    public Claims extractAllClaims(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }

    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }


    public Boolean isTokenValid(String token , UserDetails userDetails){
        String userName = extractUserName(token);
        return userName.equals(userDetails.getUsername())
                &&!isTokenExpired(token);
    }
}
