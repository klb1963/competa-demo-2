package com.competa.competademo.controller;

import com.competa.competademo.dto.CompetaDto;
import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.entity.Industry;
import com.competa.competademo.service.IndustryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndustryController {

    private final IndustryService industryService;

    public IndustryController(IndustryService industryService) {
        this.industryService = industryService;
    }

    @GetMapping("/industry")
    public String industryMain(org.springframework.ui.Model model) {
        final List<IndustryDto> industryDtoList = industryService.findAllIndustries();
        model.addAttribute("industry", industryDtoList);
        return "industry-main"; // вызывается шаблон
    }

    @GetMapping("/industry/add")  // переход на страницу
    public String competaAdd(Model model) {
        model.addAttribute(new IndustryDto());// через model связали шаблон с классом Industry
        return "competa-add";  // вызывается шаблон
    }

    @PostMapping("/industry/add")
    public String competaAdd(@ModelAttribute IndustryDto industry, Model model) {
        model.addAttribute(new IndustryDto());
        industryService.saveIndustry(industry);
        return "industry-main"; // переход на страницу redirect:/competa"
    }

}
