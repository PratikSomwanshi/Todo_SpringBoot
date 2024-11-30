package com.wanda.service;

import com.wanda.utils.exceptions.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JWTService {

    private SecretKey secretKey;

    public JWTService() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");

            keyGenerator.init(256);


            this.secretKey = keyGenerator.generateKey();

            String base64EncodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println("Base64 Encoded Secret Key: " + base64EncodedKey);


        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new CustomException("no such alorithm present");
        }
    }

    public String generate(String username) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("name", "rohan");

        return Jwts
                .builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 10))
                .signWith( getKey(), Jwts.SIG.HS256 )
                .compact();

    }

    public SecretKey getKey() {
        return this.secretKey;
    }

    public String extractEmail(String bearer) {

        if(bearer.isEmpty()) {
            throw new CustomException("Tocken Not Found");
        }

        if(!bearer.startsWith("Bearer")) {
            System.out.println(bearer);
            throw new CustomException("Invalid Tocken");
        }

        String tocken = bearer.substring(7);

        System.out.println(tocken);



        return getEmail(tocken);
    }

    public String getEmail(String token) {
        return this.getClaims(token).getSubject();
    }




    public Claims getClaims(String tocken) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(this.secretKey)
                    .build()
                    .parseSignedClaims(tocken)
                    .getPayload();
        }catch (ExpiredJwtException ex){
            throw new CustomException("Tocken Expired");
        }catch(JwtException ex){
            throw new CustomException("JWT malformed / invalid token");
        }catch(Exception ex){
            throw new CustomException(ex.getMessage());
        }
    }

}
