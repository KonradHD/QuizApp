package com.course.quiz.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.course.quiz.model.User;

@Service
public class QuizUserDetailsService implements UserDetailsService{
    private final Map<String, User> users = new HashMap<>();
    private final BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = users.get(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found.");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    public void registerUser(String username, String email, String password, String role) throws Exception{
        if(users.containsKey(username)){
            throw new Exception("User was registered.");
        }
        String encodedPassword = passEncoder.encode(password);
        users.put(username, new User(username, email, encodedPassword, role));
    }
}