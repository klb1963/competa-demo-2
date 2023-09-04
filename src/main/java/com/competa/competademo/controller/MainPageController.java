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

    @GetMapping("/about") // - page About controller
    public String about(Model model) {
        model.addAttribute("title", "About");
        return "about";
    }

}
