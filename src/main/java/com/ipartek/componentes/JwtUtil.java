package com.ipartek.componentes;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {					
	
	@Value("${jwt.secret}")
    private String SECRET_KEY;

	 public String generateToken(String username, String role) {
	        return Jwts.builder()
	                .setSubject(username)
	                .claim("rol", role)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + 60 * 1000)) // 1 minuto
	                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
	                .compact();
	    }
	 
	 	public Claims extractClaims(String token) {
	        return Jwts.parserBuilder()
	                   .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
	                   .build()
	                   .parseClaimsJws(token)
	                   .getBody();
	    }
	 	
	 	public boolean isTokenValid(String token) {
	        try {
	            Claims claims = extractClaims(token);
	            return claims.getExpiration().after(new Date());
	        } catch (Exception e) {
	            return false;
	        }
	    }



	
	
	
}
