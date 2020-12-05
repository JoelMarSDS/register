package com.cash.register.domain.settings.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private static final String USER = "/useremployee";
    private static final String CLIENT = "/client";
    private static final String CASHBOOK = "/cashbook";

    @Autowired
    private ImplementsUserDetailsService implementsUserDetailsService;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().and()
                .csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST,USER).hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,USER).hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,USER).hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,USER + "/list").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,USER + "/filter").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,USER).hasRole("ADMIN")
                .anyRequest().authenticated().and().logout();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(implementsUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
