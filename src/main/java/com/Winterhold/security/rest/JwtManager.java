package com.Winterhold.security.rest;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtManager {

    private String SECRET_KEY = "bimsalabim-adacadabra";

    private Claims getClaims(String token){
        JwtParser parser = Jwts.parser().setSigningKey(SECRET_KEY);
        Jws<Claims> signatureAndClaims = parser.parseClaimsJws(token);
        Claims claims = signatureAndClaims.getBody();
        return claims;
    }

    public String getUsername(String tokoen){
        Claims claims = getClaims(tokoen);
        return claims.get("username",String.class);
    }

    private Date getIssueDate(){
        var now = LocalDateTime.now();
        var systemTime = now.atZone(ZoneId.systemDefault()).toInstant();
        var issueDate = Date.from(systemTime);
        return issueDate;
    }

    private Date getExpireDate(LocalDateTime startTime, Integer minutes){
        var expireAt = startTime.plusMinutes(minutes);
        var systemTime = expireAt.atZone(ZoneId.systemDefault()).toInstant();
        var expireDate = Date.from(systemTime);
        return expireDate;
    }
    public String generateToke(String subject,String username, String secretKey,String role,String audience){
        JwtBuilder builder=Jwts.builder();
        var issueDate = getIssueDate();
        var expireDate = getExpireDate(LocalDateTime.now(),60);
        builder = builder.setSubject(subject)
                .claim("username",username)
                .claim("role",role)
                .setIssuer("http://localhost:7075")
                .setAudience(audience)
                .setIssuedAt(issueDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256,secretKey);
        return builder.compact();
    }

    public Boolean validateToke(String token, UserDetails userDetails){
        Claims claims = getClaims(token);
        String user = claims.get("username",String.class);
        String audience =claims.getAudience();
        return (user.equals(userDetails.getUsername()));
    }
}
