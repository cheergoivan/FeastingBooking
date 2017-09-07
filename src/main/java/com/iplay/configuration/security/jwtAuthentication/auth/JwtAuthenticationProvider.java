package com.iplay.configuration.security.jwtAuthentication.auth;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.iplay.configuration.security.jwtAuthentication.JwtFactory;
import com.iplay.configuration.security.jwtAuthentication.JwtPayload;


@Component
public class JwtAuthenticationProvider implements AuthenticationProvider{
	@Autowired
	private JwtFactory jwt;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = (String) authentication.getCredentials();
		JwtPayload payload = jwt.getPayload(token);
		UserContext context = new UserContext(payload.getUserId(), payload.getSubject(), payload.getRole());
		Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority
				(context.getRole().authority()));
		return new JwtAuthenticationToken(context,authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
