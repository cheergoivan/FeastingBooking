package com.iplay.configuration.security.jwtAuthentication;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.iplay.configuration.security.JwtConfigurationProperties;
import com.iplay.configuration.security.jwtAuthentication.auth.UserContext;
import com.iplay.configuration.security.jwtAuthentication.exception.JwtExpiredException;
import com.iplay.entity.user.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
@EnableConfigurationProperties(JwtConfigurationProperties.class)
public class JwtFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtFactory.class);
	
	@Autowired
	private JwtConfigurationProperties jwtProp;
	
	public String generateToken(UserContext context) {
		Claims claims =  Jwts.claims().setSubject(context.getUsername());
		claims.put(JwtPayload.CLAIM_KEY_USERID, context.getUserId());
		claims.put(JwtPayload.CLAIM_KEY_ROLE, context.getRole());
		return buildJwtWithExpirationTime(claims);
	}
	
	public String generateRegistrationToken(String email){
		Claims claims =  Jwts.claims().setSubject(email);
		return buildJwtWithExpirationTime(claims);
	}
	
	private String buildJwtWithExpirationTime(Claims claims){
		JwtBuilder builder =Jwts.builder().setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, jwtProp.getSecretKey());
		if(jwtProp.getTokenExpirationTime()!=-1)
			builder.setExpiration(generateExpirationDate());
		return builder.compact();
	}
	
	public String deserializeRegistrationToken(String token){
		Claims claims= getClaimsFromToken(token);
		return claims.getSubject();
	}

	public JwtPayload getPayload(String token){
		Claims claims= getClaimsFromToken(token);
		JwtPayload payload = new JwtPayload();
		payload.setSubject(claims.getSubject());
		if(claims.get(JwtPayload.CLAIM_KEY_ROLE)==null||claims.get(JwtPayload.CLAIM_KEY_USERID)==null){
            throw new BadCredentialsException("Invalid token!");
		}
		payload.setRole(Role.valueOf((String) claims.get(JwtPayload.CLAIM_KEY_ROLE)));
		payload.setUserId(toLong(claims.get(JwtPayload.CLAIM_KEY_USERID)));
		return payload;
	}
	
	private Claims getClaimsFromToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(jwtProp.getSecretKey())
					.parseClaimsJws(token).getBody();
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            logger.info("Invalid token!", ex);
            throw new BadCredentialsException("Invalid token!", ex);
        } catch (ExpiredJwtException expiredEx) {
            logger.info("The token has expired!", expiredEx);
            throw new JwtExpiredException( "The token has expired!");
        }
		return claims;
	}
	

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + jwtProp.getTokenExpirationTime() * 60 * 1000);
	}
	
	private long toLong(Object o){
		if(o instanceof Long){
			return (Long)o;
		}
		return ((Integer)o).longValue();
	}
}
