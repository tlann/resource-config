package com.example.utils.security.resource.autoconfig;

import mil.noms.utils.security.utils.components.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;


@Configuration
@EnableWebSecurity
@Order(PriorityOrdered.HIGHEST_PRECEDENCE + 500)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromOidcIssuerLocation(issuerUri);
    }

    @Bean
    public AuthenticationFacade authenticationFacade(){
        return new AuthenticationFacade();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .anonymous().disable()
                .httpBasic().disable()
                .logout().disable()
                .formLogin().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .antMatchers("/actuator/**").access("#oauth2?.hasScope('noms-auditor')")
                .antMatchers("/actuator/**").hasAuthority("SCOPE_noms-auditor")
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt();
    }

}
