package com.course.quiz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.course.quiz.model.Question;

@Service
public class QuestionService {
    private final Map<Integer, Question> questions = new HashMap<>();

    public List<Question> loadQuizzes(){
        List<Question> questionsList = new ArrayList<>();
        for(Map.Entry<Integer, Question> entry : questions.entrySet()){
            questionsList.add(entry.getValue());
        }
        return questionsList;
    }

    public boolean addQuiz(Question question){
        if(!questions.containsKey(question.getId())){
            questions.put(question.getId(), question);
            return true;
        }
        return false;
    }

    public boolean editQuiz(Question question){
        if(questions.containsKey(question.getId())){
            questions.replace(question.getId(), question);
            return true;
        }
        return false;
    }

    public boolean deleteQuiz(int id){
        if(questions.containsKey(id)){
            questions.remove(id);
            return true;
        }
        return false;
    }

    public ArrayList<Question> getQuestions(){
        ArrayList<Question> ques = new ArrayList<>(questions.values());
        return ques;
    }

    public Question getQuestionById(int id){
        return questions.get(id);
    }

    public int getNextId(){
        return questions.size() + 1;
    }
}
