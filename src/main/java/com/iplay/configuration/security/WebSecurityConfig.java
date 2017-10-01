package com.iplay.configuration.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.iplay.configuration.security.jwtAuthentication.auth.JwtAuthenticationProvider;
import com.iplay.configuration.security.jwtAuthentication.web.JwtAuthenticationProcessingFilter;
import com.iplay.configuration.security.jwtAuthentication.web.RestAuthenticationEntryPoint;
import com.iplay.configuration.security.util.SkipPathRequestMatcher;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(APIFilterConfigurationProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private APIFilterConfigurationProperties apis;
    
    @Autowired 
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    
    @Autowired 
    private RestAuthenticationEntryPoint authenticationEntryPoint;
    
    protected JwtAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
    	List<String> pathsToSkip = apis.getWhiteList();
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, apis.getProtectedResources());
        JwtAuthenticationProcessingFilter filter 
            = new JwtAuthenticationProcessingFilter(matcher);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
	@Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable() // We don't need CSRF for JWT based authentication
        .exceptionHandling()
        .authenticationEntryPoint(this.authenticationEntryPoint)
        
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
            .authorizeRequests()
                .antMatchers(apis.getWhiteListUrls().toArray(new String[1])).permitAll() // Login end-point
        .and()
            .authorizeRequests()
                .antMatchers(apis.getProtectedResources()).authenticated() // Protected API End-points
        .and()
           // .addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
