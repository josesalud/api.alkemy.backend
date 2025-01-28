package com.alkemy.technical.test.service;

import com.alkemy.technical.test.entities.Users;
import com.alkemy.technical.test.config.properties.PropertiesConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@Slf4j
public class JwtService {

    @Autowired
    PropertiesConfig propertiesConfig;

    public String generateToken(final Users user){
        log.info(propertiesConfig.toString());
        return this.buildToken(user,this.propertiesConfig.expiration());
    }

    public  String generateRefreshToken(final Users user){
        return this.buildToken(user,this.propertiesConfig.refreshToken().getExpiration());
    }

    public String extractUserName(final String token){
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSingInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();
    }

    public boolean isTokenExpired(final String token ){
        return this.extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(final String token){
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSingInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getExpiration();
    }

    private String buildToken(final Users user, final long expiration){
        return Jwts.builder()
                .id(user.getId().toString())
                .claim("name",user.getName())
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(this.getSingInKey())
                .compact();

    }

    private SecretKey getSingInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(this.propertiesConfig.secretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.propertiesConfig.secretKey()).build().parseClaimsJws(token);
            return true;
        }
        catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT token is not valid " + token);
        }
    }


}
