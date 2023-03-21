package com.burat.simpel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/register").hasAuthority("admin")
                .antMatchers("/account-management/**").hasAuthority("admin")
                .antMatchers("/comp-dict/add").hasAuthority("admin")
                .antMatchers("/comp-dict/update").hasAuthority("admin")
                .antMatchers("/comp-dict/delete/**").hasAuthority("admin")
                .antMatchers("/comp-dict/detail").permitAll()
                .antMatchers("/comp-dict").permitAll()
                .antMatchers("/competency-model/add").hasAuthority("admin")
                .antMatchers("/competency-model/delete/**").hasAuthority("admin")
                .antMatchers("/competency-model/update/**").hasAuthority("admin")
                .antMatchers("/competency-model").permitAll()
                .antMatchers("/training-catalog/add").hasAuthority("admin")
                .antMatchers("/training-catalog/delete/**").hasAuthority("admin")
                .antMatchers("/training-catalog/update/**").hasAuthority("admin")
                .antMatchers("/training-catalog/detail").permitAll()
                .antMatchers("/training-catalog").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll();
        return http.build();
    }

    public BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
}
