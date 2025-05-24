package com.course.quiz.model;

import java.util.ArrayList;
import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Question {
    private int id; 
    private String questionText;
    private ArrayList<String> options;
    private String correctAnswer;

    public Question(){
        options = new ArrayList<>();
    }

    public Question(int id, String questionText, ArrayList<String> options, String correctAnswer){
        this.id = id;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.options = new ArrayList<>();
        for(String option : options){
            this.options.add(option);
        }
    }

    // Helper method to get options as a comma-separated string
    public String getOptionsAsString() {
        return String.join(",", options);
    }

     // Helper method to set options from a comma-separated string
     public void setOptionsFromString(String optionsString) {
        this.options = new ArrayList<>(Arrays.asList(optionsString.split(",")));
    }
}
