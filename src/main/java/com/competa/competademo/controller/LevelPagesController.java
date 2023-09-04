package com.competa.competademo.controller;

import com.competa.competademo.dto.CtypeDto;
import com.competa.competademo.dto.LevelDto;
import com.competa.competademo.service.LevelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class LevelPagesController {
    private static final String LEVEL = "level";
    private static final String LEVEL_VIEW_VARIABLE = "level";
    private static final String REDIRECT_LEVEL = "redirect:/level";
    private final LevelService levelService;

    public LevelPagesController(LevelService levelService) {
        this.levelService = levelService;
    }

    @GetMapping("/level")
    public String levelMain(org.springframework.ui.Model model) {
        final List<LevelDto> levelDtoList = levelService.findAllById();
        // TODO - отсортировать по порядку ID
        model.addAttribute("levels", levelDtoList);
        return "level-main"; // вызывается шаблон
    }

    @GetMapping("/level/add")  // переход на страницу
    public String levelAdd(Model model) {
        model.addAttribute(LEVEL_VIEW_VARIABLE, new LevelDto());// через model связали шаблон с классом Industry
        return "level-add";  // вызывается шаблон
    }

    @PostMapping("/level/add")
    public String levelAdd(@ModelAttribute LevelDto level, Model model) {
        model.addAttribute(LEVEL, new LevelDto());
        levelService.saveLevel(level);
        return REDIRECT_LEVEL; // переход на страницу redirect:/industry
    }

    @GetMapping("/level/{id}")  // переход на страницу
    public String levelDetails(@PathVariable(value = "id") long id, Model model) {
        final Optional<LevelDto> level = levelService.findById(id);
        if (level.isPresent()) {
            model.addAttribute(LEVEL_VIEW_VARIABLE, level.get());
            return "level-details";
        } else {
            return REDIRECT_LEVEL;
        }
    }

    @GetMapping("/level/{id}/edit")
    public String levelEdit(@PathVariable(value = "id") long id, Model model) {
        final Optional<LevelDto> level = levelService.findById(id);// взяли "футляр"
        if (level.isPresent()) {  // если внутри "футляра" есть результат
            model.addAttribute(LEVEL_VIEW_VARIABLE, level.get()); // взяли в model
            return "level-edit";
        } else {
            return REDIRECT_LEVEL;
        }
    }

    @PostMapping("/level/{id}/edit") //
    public String levelUpdate(@PathVariable(value = "id") long id, @ModelAttribute LevelDto level) {
        levelService.saveLevel(level);
        return REDIRECT_LEVEL;
    }

    @PostMapping("/level/{id}/remove")
    public String levelDelete(@PathVariable(value = "id") long id) {
        levelService.removeLevel(id);
        return REDIRECT_LEVEL;
    }

}
