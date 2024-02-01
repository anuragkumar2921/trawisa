package com.backend.trawisa.security.jwt;

import com.backend.trawisa.security.properties.ConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {


    private ConfigProperties properties;

    private final SecretKey secretKey;

    @Autowired
    public JwtHelper(ConfigProperties properties) {
        this.properties = properties;
        byte[] apiKeySecretBytes = Base64.getEncoder().encode(properties.getJwtSecretKey().getBytes());
        this.secretKey = new SecretKeySpec(apiKeySecretBytes, "HmacSHA256");
    }

    //requirement :
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // second
//    public static final long JWT_TOKEN_VALIDITY =  10;

    //    public static final long JWT_TOKEN_VALIDITY =  60;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) throws SignatureException {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws SignatureException {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) throws SignatureException{
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        return  jwtParser.parseClaimsJws(token).getBody();
    }


    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)); // millisecond

        return jwtBuilder.signWith(secretKey).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}