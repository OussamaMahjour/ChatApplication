package com.oussama.socialmedia.gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;


@Component
public class Utils {

    @Value(value = "${jwt.secretKey}")
    public static String secretKey ="83a4ba15634f0d4d547c6f63f5a23bd02ff297aa4dff71e91dfd6754fb50bbbd";


    public boolean isTokenExpired(String token) {

        return getClaimFromToken(token, Claims::getExpiration).before(new Date());
    }

    public boolean isTokenValid(String token) {
        try{
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
        }catch(
    SignatureException e){
        return false;
    }
        return !isTokenExpired(token);
    }

    public Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }


    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }



    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
