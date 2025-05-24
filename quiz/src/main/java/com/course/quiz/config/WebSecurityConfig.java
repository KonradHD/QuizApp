package com.course.quiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.course.quiz.service.QuizUserDetailsService;

import lombok.AllArgsConstructor;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {
    private final QuizUserDetailsService quizUserDetails;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/login", "/register").permitAll()
            .requestMatchers("/quizList").hasRole("ADMIN")
            .requestMatchers("/quiz").hasRole("USER")
            .anyRequest().permitAll()
        )
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/home", true) // po zalogowaniu przekierowuje do tej strony
            .permitAll()
        )
        .logout(out -> out
            .permitAll()
        );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authManBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authManBuilder
            .userDetailsService(quizUserDetails)
            .passwordEncoder(new BCryptPasswordEncoder());
        return authManBuilder.build();
    } 

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

