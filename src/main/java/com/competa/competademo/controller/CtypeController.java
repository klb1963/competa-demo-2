package com.competa.competademo.controller;

import com.competa.competademo.dto.CtypeDto;
import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.service.CtypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class CtypeController {
    private static final String CTYPE = "ctype";
    private static final String CTYPE_VIEW_VARIABLE = "ctype";
    private static final String REDIRECT_CTYPE = "redirect:/ctype";
    private final CtypeService ctypeService;

    public CtypeController(CtypeService ctypeService) {
        this.ctypeService = ctypeService;
    }

    @GetMapping("/ctype")
    public String ctypeMain(org.springframework.ui.Model model) {
        final List<CtypeDto> ctypeDtoList = ctypeService.findAllById();
        model.addAttribute("ctypeList", ctypeDtoList);
        return "ctype-main"; // вызывается шаблон
    }

    @GetMapping("/ctype/add")  // переход на страницу
    public String ctypeAdd(Model model) {
        model.addAttribute(CTYPE_VIEW_VARIABLE, new CtypeDto());// через model связали шаблон с классом Industry
        return "ctype-add";  // вызывается шаблон
    }

    @PostMapping("/ctype/add")
    public String competaAdd(@ModelAttribute CtypeDto ctype, Model model) {
        model.addAttribute(CTYPE, new CtypeDto());
        ctypeService.saveCtype(ctype);
        return REDIRECT_CTYPE; // переход на страницу redirect:/industry
    }

    @GetMapping("/ctype/{id}")  // переход на страницу
    public String ctypeDetails(@PathVariable(value = "id") long id, Model model) {
        final Optional<CtypeDto> ctype = ctypeService.findById(id);
        if (ctype.isPresent()) {
            model.addAttribute(CTYPE_VIEW_VARIABLE, ctype.get());
            return "ctype-details";
        } else {
            return REDIRECT_CTYPE;
        }
    }

    @GetMapping("/ctype/{id}/edit")
    public String ctypeEdit(@PathVariable(value = "id") long id, Model model) {
        final Optional<CtypeDto> ctype = ctypeService.findById(id);// взяли "футляр"
        if (ctype.isPresent()) {  // если внутри "футляра" есть результат
            model.addAttribute(CTYPE_VIEW_VARIABLE, ctype.get()); // взяли в model
            return "ctype-edit";
        } else {
            return REDIRECT_CTYPE;
        }
    }

    @PostMapping("/ctype/{id}/edit") //
    public String ctypeUpdate(@PathVariable(value = "id") long id, @ModelAttribute CtypeDto ctype) {
        ctypeService.saveCtype(ctype);
        return REDIRECT_CTYPE;
    }

    @PostMapping("/ctype/{id}/remove")
    public String ctypeDelete(@PathVariable(value = "id") long id) {
        ctypeService.removeCtype(id);
        return REDIRECT_CTYPE;
    }

}
