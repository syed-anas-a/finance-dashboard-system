package com.syed.fds.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	
	@Value("${jwt.secretKey}")
	private String jwtSecretKey;
	
	@Value("${jwt.expiration}")
	private long jwtExpiration;
	
	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateAccessToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(getSecretKey())
				.compact();
	}

	public String extractUsername(String token) {
		
		return Jwts.parserBuilder()
	            .setSigningKey(getSecretKey())
	            .build()
	            .parseClaimsJws(token)
	            .getBody()
	            .getSubject(); 
	} 
	
	public boolean validateToken(String token) {
		try {
	        Jwts.parserBuilder()
	            .setSigningKey(getSecretKey())
	            .build()
	            .parseClaimsJws(token);
	        return true;
	    } catch (ExpiredJwtException e) {
	        logger.error("Token expired: {}", e.getMessage());
	    } catch (JwtException e) {
	        logger.error("Invalid token: {}", e.getMessage());
	    } catch (Exception e) {
	        logger.error("Token validation error: {}", e.getMessage());
	    }
	    return false;
		
	}

}
