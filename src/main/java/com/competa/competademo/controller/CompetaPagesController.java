package com.competa.competademo.controller;

import com.competa.competademo.dto.CompetaDto;
import com.competa.competademo.service.CompetaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class CompetaPagesController {

    private static final String COMPETA_VIEW_VARIABLE = "competa";
    private static final String REDIRECT_COMPETA = "redirect:/competa";

    private final CompetaService<CompetaDto> competaService;


    public CompetaPagesController(CompetaService<CompetaDto> competaService) {
        this.competaService = competaService;
    }

    @GetMapping("/competa")
    public String competaMain(Model model) {
        final List<CompetaDto> userCompetas = competaService.findAllByAuthUser();
        model.addAttribute("competas", userCompetas);
        return "competa-main"; // вызывается шаблон
    }

    @GetMapping("/competa/add")  // переход на страницу
    public String competaAdd(Model model) {
        model.addAttribute(COMPETA_VIEW_VARIABLE, new CompetaDto());// через model связали шаблон с классом Competa
        return "competa-add";  // вызывается шаблон
    }

    @PostMapping("/competa/add")
    public String competaAdd(@ModelAttribute CompetaDto competa, Model model) {
        model.addAttribute(COMPETA_VIEW_VARIABLE, new CompetaDto());
        competaService.addToAuthUser(competa);
        return REDIRECT_COMPETA; // переход на страницу redirect:/competa"
    }

    @GetMapping("/competa/{id}")  // переход на страницу
    public String competaDetails(@PathVariable(value = "id") long id, Model model) {
        final Optional<CompetaDto> competa = competaService.findById(id);
        if (competa.isPresent()) {
            model.addAttribute(COMPETA_VIEW_VARIABLE, competa.get());
            return "competa-details";
        } else {
            return REDIRECT_COMPETA;
        }
    }

    @GetMapping("/competa/{id}/edit")
    public String competaEdit(@PathVariable(value = "id") long id, Model model) {
        final Optional<CompetaDto> competa = competaService.findById(id); // взяли "футляр"
        if (competa.isPresent()) {  // если внутри "футляра" есть результат
            model.addAttribute(COMPETA_VIEW_VARIABLE, competa.get()); // взяли в model
            return "competa-edit";
        } else {
            return REDIRECT_COMPETA;
        }
    }

    @PostMapping("/competa/{id}/edit") //
    public String competaUpdate(@PathVariable(value = "id") long id, @ModelAttribute CompetaDto competa) {
        competaService.update(id, competa);
        return REDIRECT_COMPETA;
    }

    @PostMapping("/competa/{id}/remove")
    public String competaDelete(@PathVariable(value = "id") long id) {
        competaService.remove(id);
        return REDIRECT_COMPETA;
    }
}
