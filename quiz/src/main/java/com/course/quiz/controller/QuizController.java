package com.course.quiz.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.course.quiz.model.Question;
import com.course.quiz.service.QuestionService;
import com.course.quiz.service.QuizUserDetailsService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class QuizController {
    private final QuestionService questionService;
    private final QuizUserDetailsService userService;
    private final AuthenticationManager authenticationManager; // żeby działał tak jak chcemy musimy mieć klase @EnableWebSecurity


    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password,
        @RequestParam String email, @RequestParam String role){
        try{
            userService.registerUser(username, email, password, role);

        }catch(Exception e){
            System.out.println(e.toString());
            return "redirect:/register?error";
        }
        
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)); // tworzy instancje authenticate i uzupełnia ją danymi o userze
    
        SecurityContextHolder.getContext().setAuthentication(authentication);// dodaje autentykacje do kontenera

        return "redirect:/login?success";
        }

    @GetMapping("/addQuiz")
    public String quizPage(Model model){
        model.addAttribute("question", new Question());
        return "quiz";
    }

    @PostMapping("/addQuiz")
    public String getQuestions(@ModelAttribute Question question, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .findFirst()
            .orElse("OTHER");
        if(role.equals("ADMIN")){
            question.setId(questionService.getNextId());
            if(questionService.addQuiz(question)){
                model.addAttribute("success", "Question added successfully.");
                return "redirect:/home";
            }
            return "redirect:/home";
        }
        model.addAttribute("error", "You do not have permission to add a quiz.");
	    return "redirect:/home";

    }

    @GetMapping("/editQuiz/{id}")
    public String getQuiz(@PathVariable int id, Model model){
        Question que = questionService.getQuestionById(id);
        model.addAttribute("question", que);
        return "editQuiz";
    }

    @PostMapping("/editQuiz")
    public String editQuestion(@ModelAttribute Question question, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .findFirst()
            .orElse("OTHER");

        if(role.equals("ADMIN")){
            if(questionService.editQuiz(question)){
                model.addAttribute("success", "Question changed successfully.");
                return "redirect:/home";
            }
            model.addAttribute("error", "Question was not changed successfully.");
            return "redirect:/home";
        }
        model.addAttribute("error", "You do not have permission to change a quiz.");
	    return "redirect:/home";
    }


    @DeleteMapping("/editQuiz/{id}")
    public String deleteQuestion(@PathVariable int id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .findFirst()
            .orElse("OTHER");

        if(role.equals("ADMIN")){
            if(questionService.deleteQuiz(id)){
                model.addAttribute("success", "Question deleted successfully.");
                return "redirect:/home";
            }
            model.addAttribute("error", "Question was not deleted successfully.");
            return "redirect:/home";
        }
        model.addAttribute("error", "You do not have permission to delete a quiz.");
	    return "redirect:/home";
    }


    @GetMapping("/home")
    public String homePage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            return "redirect:/login";
        }

        String name = authentication.getName();
        model.addAttribute("username", name);
        
        String role = authentication.getAuthorities().stream() // zwraca kolekcje ról
                                    .map(GrantedAuthority::getAuthority) //zamienia każdy GrantedAuthority na jego nazwę jako String
                                    // gdyby nie ta linijka to zapytanie zwróciłoby obiekt typu GrantedAuthority
                                    //GrantedAuthority::getAuthority oznacza żeby na każdym obiekcie typu GrantedAuthority użyć metody getAthority
                                    // (authority -> authority.getAuthority()) - to samo   
                                    .findFirst()
                                    .orElse("other");
        
        List<Question> questions = questionService.getQuestions();
        model.addAttribute("questions", questions);

        if(role.equals("ROLE_ADMIN")){
            return "quizList";
        }
        return "quiz";
    }


    @PostMapping("/submitQuiz")
    public String addAnswer(@RequestParam Map<String, String> answers, Model model){
        int correctAnswers = 0;
        List<String> userAnswers = new ArrayList<>(answers.values());
        ArrayList<Question> questions = questionService.getQuestions();
        for(int i = 0; i < questions.size(); i++){
            String answer = answers.get("answer" + i);
            if(answer.equals(questions.get(i).getCorrectAnswer())){
                correctAnswers++;
            }
        }
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("questions", questions);
        model.addAttribute("totalQuestions", questions.size());
        model.addAttribute("userAnswers", userAnswers);
        return "result";
    }

    @GetMapping("/result")
    public String getResults(){
        return "result";
    }
}
