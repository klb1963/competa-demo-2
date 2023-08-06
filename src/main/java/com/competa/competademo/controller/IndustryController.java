package com.competa.competademo.controller;

import com.competa.competademo.dto.CompetaDto;
import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.entity.Industry;
import com.competa.competademo.service.IndustryService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class IndustryController {

    private static final String INDUSTRY = "industry";

    private static final String INDUSTRY_VIEW_VARIABLE = "industry";
    private static final String REDIRECT_INDUSTRY = "redirect:/industry";
    private final IndustryService industryService;

    public IndustryController(IndustryService industryService) {
        this.industryService = industryService;
    }

    @GetMapping("/industry")
    public String industryMain(org.springframework.ui.Model model) {
        final List<IndustryDto> industryDtoList = industryService.findAllById();
        model.addAttribute("industries", industryDtoList);
        return "industry-main"; // вызывается шаблон
    }

    @GetMapping("/industry/add")  // переход на страницу
    public String industryAdd(Model model) {
        model.addAttribute(INDUSTRY_VIEW_VARIABLE, new IndustryDto());// через model связали шаблон с классом Industry
        return "industry-add";  // вызывается шаблон
    }

    @PostMapping("/industry/add")
    public String competaAdd(@ModelAttribute IndustryDto industry, Model model) {
        model.addAttribute(INDUSTRY, new IndustryDto());
        industryService.saveIndustry(industry);
        return REDIRECT_INDUSTRY; // переход на страницу redirect:/industry
    }
    @GetMapping("/industry/{id}")  // переход на страницу
    public String industryDetails(@PathVariable(value = "id") long id, Model model) {
       final Optional<IndustryDto> industry = industryService.findById(id);
       if (industry.isPresent()) {
           model.addAttribute(INDUSTRY_VIEW_VARIABLE, industry.get());
           return "industry-details";
       } else {
           return REDIRECT_INDUSTRY;
       }
    }
    @GetMapping("/industry/{id}/edit")
    public String industryEdit(@PathVariable(value = "id") long id, Model model) {
        final Optional<IndustryDto> industry = industryService.findById(id);// взяли "футляр"
        if (industry.isPresent()) {  // если внутри "футляра" есть результат
            model.addAttribute(INDUSTRY_VIEW_VARIABLE, industry.get()); // взяли в model
            return "industry-edit";
        } else {
            return REDIRECT_INDUSTRY;
        }
    }







    @PostMapping("/industry/{id}/edit") //
    public String industryUpdate(@PathVariable(value = "id") long id, @ModelAttribute IndustryDto industry) {
        industryService.saveIndustry(industry);
        return REDIRECT_INDUSTRY;
    }

    @PostMapping("/industry/{id}/remove")
    public String industryDelete(@PathVariable(value = "id") long id) {
        industryService.removeIndustry(id);
        return REDIRECT_INDUSTRY;
    }
}
