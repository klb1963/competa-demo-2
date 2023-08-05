package com.competa.competademo.controller;

import com.competa.competademo.dto.CreateUserDto;
import com.competa.competademo.dto.UserDto;
import com.competa.competademo.exceptions.UserAlreadyExistsException;
import com.competa.competademo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    // поле класса
    private final UserService userService;

    // конструктор
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // handler method to handle home page "/" request
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // handler method to handle home page "/home"request
    @GetMapping("/home")
    public String home1() {
        return "home";
    }

    // handler method to handle home page "index" request
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        CreateUserDto user = new CreateUserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") CreateUserDto user,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        try {
            userService.saveUser(user);
            return "redirect:/register?success";
        } catch (UserAlreadyExistsException ex) {
            result.rejectValue("email", "", ex.getMessage());
            model.addAttribute("user", user);
            return "register";
        }
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}
