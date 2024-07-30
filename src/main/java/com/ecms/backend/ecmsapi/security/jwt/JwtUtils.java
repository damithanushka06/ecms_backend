package com.ecms.backend.ecmsapi.security.jwt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${apExpress.app.jwtSecret}")
  private String jwtSecret;

  @Value("${apExpress.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public String generateJwtToken(Authentication authentication) {

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + jwtExpirationMs) )
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact();

  }

  public boolean validateJwtToken(String authToken) {

    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      System.err.println("Invalid token");
    } catch(ExpiredJwtException e) {
      System.err.println("Expired token");
    } catch (UnsupportedJwtException e) {
      System.err.println("Unsupported token format");
    } catch(IllegalArgumentException e) {
      System.err.println("Token blank");
    }

    return false;
  }

  public String getUsernameFromJwtToken(String authToken) {
    return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken).getBody().getSubject();
  }
}
