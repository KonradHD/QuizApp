package com.course.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User{
    private String username;
    private String email;
    private String password; 
    private String role;

    @Override
    public String toString(){
        return "Username: " + username + "\nEmail: " + email + "\nRole: " + role;
    }
}