package com.programming.springtutorial.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {


//    public static final String SECRET = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
    public static final String SECRET = "" +
        "oTl2Q6jns89hAAE59LMUpZ0YEmMGoodmxFmkSjOCdi4cMsE/LKntWHaaBQTSI2UKT2ffgB2A58bPUqS5qUre8vsvix4IOa/KIrOJd8P164+egZt" +
        "5uRp4wAqJfq0UDMcGSSnRrVq+JWUtp5r2UEC34wZrH3M0lexvslajD6AVNtfyygRgLCESGD9sFFHI6lNoHJZSlWxkM85EeS5mmei6jCIs5XxeDX" +
        "5qMr9XiGTnB2c2E9DbNkRpL/ttXzKts0IuKYOaRvY8oLKtqJ8lmu30WRTLR9aezK++Wl0bo85zJuTp4aDY28WIwBGoKUQFUz2FfwtI9GWXrLnXm" +
        "zvxMnuwPJTfEy1uG6o+bLkY33f8aaJAYCVoWHfH33vpAZ3LU32yUL3hUQnnoyVA4UpLMZXkQIHxjSagPOnq3wm9z1pFG44ndrxjCPpT1dJVNTTW" +
        "agvfq+HmrwwDmaddqwve2JC51gxy7awC0DZSnE9UHcc8/3CN0CrFXiXq6GEBQ4GgFbo5WiRuxJJO0/Qq5LFgUVUzQ/AGpbByNNh9/vXkmTshbh9" +
        "RA+CDT9ccbSWmlgi9RLqYFxMGc4qMTeS/kWU8Bxa98p8BMQthOY1ZTOFI7bQQh+FEcdB1Ll0HrSw5qTM9DPD3bGosm1aYGOMDiuoCMDuFeNFJFq" +
        "uTt/q0F38uYMLbrbySao5+Q2aRjoXXIzFfW5Eu";


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public String generateToken(String userName){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*3))
                .signWith(getSignKey(), SignatureAlgorithm.HS512).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
