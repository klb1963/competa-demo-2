package com.competa.competademo.controller;

import com.competa.competademo.dto.CompetaDto;
import com.competa.competademo.dto.UserDto;
import com.competa.competademo.entity.ImageInfo;
import com.competa.competademo.entity.User;
import com.competa.competademo.service.CompetaService;
import com.competa.competademo.service.FilesStorageService;
import com.competa.competademo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainPageController {

    private final UserService userService;
    private final CompetaService<CompetaDto> competaService;

    @Autowired
    FilesStorageService storageService;

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
        String avatarData = userService.getAvatar(authUser);
        model.addAttribute("title", "My profile");
        model.addAttribute("user", new UserDto(authUser, avatarData));
        model.addAttribute("competas", competaService.countByAuthUser());
        return "profile";
    }

    @PostMapping("/user")
    public String uploadImage(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            ImageInfo avatar = storageService.save(file);
            userService.addAvatar(avatar);
            message = "Uploaded the image successfully: " + file.getOriginalFilename();
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not upload the image: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
    return "redirect:/user";
    }
}
