package com.competa.competademo.controller;

import com.competa.competademo.dto.CompetaDto;
import com.competa.competademo.dto.UserDto;
import com.competa.competademo.entity.User;
import com.competa.competademo.service.CompetaService;
import com.competa.competademo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    private final UserService userService;
    private final CompetaService<CompetaDto> competaService;

    public MainPageController(UserService userService, CompetaService<CompetaDto> competaService) {
        this.userService = userService;
        this.competaService = competaService;
    }

    @GetMapping("/about") // - page About controller
    public String about(Model model) {
        model.addAttribute("title", "About");
        return "about";
    }

    @GetMapping("/user")
    public String profile(Model model) {
        final User authUser = userService.getAuthUser();
        model.addAttribute("title", "My profile");
        model.addAttribute("user", new UserDto(authUser));
        model.addAttribute("competas", competaService.countByAuthUser());
        return "profile";
    }

}
