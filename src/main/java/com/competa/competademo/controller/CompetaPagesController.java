package com.competa.competademo.controller;

import com.competa.competademo.dto.CompetaDto;
import com.competa.competademo.dto.CtypeDto;
import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.entity.ImageInfo;
import com.competa.competademo.service.CompetaService;
import com.competa.competademo.service.CtypeService;
import com.competa.competademo.service.FilesStorageService;
import com.competa.competademo.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Controller
public class CompetaPagesController {

    private static final String COMPETA_VIEW_VARIABLE = "competa";
    private static final String REDIRECT_COMPETA = "redirect:/competa";

    private final CompetaService<CompetaDto> competaService; // добавил сервис связи с БД

    private final IndustryService industryService; // добавил сервис связи с БД

    private final CtypeService ctypeService; // добавил сервис связи с БД

    @Autowired
    FilesStorageService storageService; // добавил сохранение изображений


    public CompetaPagesController(CompetaService<CompetaDto> competaService, IndustryService industryService, CtypeService ctypeService) {
        this.competaService = competaService;
        this.industryService = industryService;
        this.ctypeService = ctypeService;
    }

    @GetMapping("/competa")
    public String competaMain(Model model) {
        final List<CompetaDto> userCompetas = competaService.findAllByAuthUser();
        model.addAttribute("competas", userCompetas);
        return "competa-main"; // вызывается шаблон
    }

    @GetMapping("/competa/add")  // переход на страницу
    public String competaAdd(Model model) {
        final List<IndustryDto> industryDtoList = industryService.findAllById(); // взял список индустрий
        model.addAttribute("industries", industryDtoList); // передал в модель
        final List<CtypeDto> ctypeDtoList = ctypeService.findAllById(); // взял список типов компет
        model.addAttribute("ctypes", ctypeDtoList); // передал в модель
        model.addAttribute(COMPETA_VIEW_VARIABLE, new CompetaDto());// через model связал шаблон с классом Competa
        return "competa-add";  // вызывается шаблон
    }

    @PostMapping("/competa/add")
    public String competaAdd(@ModelAttribute CompetaDto competa, Model model) {
        final List<IndustryDto> industryDtoList = industryService.findAllById(); // взял список индустрий
        final List<CtypeDto> ctypeDtoList = ctypeService.findAllById(); // взял список типов компет
        model.addAttribute(COMPETA_VIEW_VARIABLE, new CompetaDto());
        competaService.addToAuthUser(competa);
        return REDIRECT_COMPETA; // переход на страницу redirect:/competa"
    }

    @GetMapping("/competa/{id}")  // переход на страницу
    public String competaDetails(@PathVariable(value = "id") long id, Model model) {
        long competaId = id;
//        final User authUser = userService.getAuthUser();
//        String avatarData = userService.getAvatar(authUser);
        final Optional<CompetaDto> competa = competaService.findById(id);
        // объект competa сейчас имеет тип Optional<CompetaDto>
        // его надо трансформировать в тип Competa, чтобы передать в getCompetaImage
        if (competa.isPresent()) {
            CompetaDto competaDto = competa.get();
            model.addAttribute(COMPETA_VIEW_VARIABLE, competaDto);
            //TODO - вернуть картинку по умолчанию, если ее нет
            if (competaDto.getCompetaImage() != null) {
                model.addAttribute("image", storageService.getBase64Image(Path.of(competaDto.getCompetaImage().getUrl())));
            }
            return "competa-details";
        } else {
            return REDIRECT_COMPETA;
        }
    }

    @PostMapping("/competa/{id}")
    public String uploadCompetaImage(@PathVariable(value = "id") long id, Model model, @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            // сохранение файла изображения компеты
            ImageInfo competaImage = storageService.save(file);
            // TODO
            long competaId = id; // параметр id взят из url c помощью @Pathvariable
            competaService.addCompetaImage(competaId, competaImage); // метод реализован
            message = "Uploaded the image successfully: " + file.getOriginalFilename();
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not upload the image: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "redirect:/competa/{id}";
    }

    @GetMapping("/competa/{id}/edit")
    public String competaEdit(@PathVariable(value = "id") long id, Model model) {
        final Optional<CompetaDto> competa = competaService.findById(id); // взяли "футляр"
        if (competa.isPresent()) {  // если внутри "футляра" есть результат
            final List<IndustryDto> industryDtoList = industryService.findAllById(); // взял список индустрий
            model.addAttribute("industries", industryDtoList); // передал в модель
            final List<CtypeDto> ctypeDtoList = ctypeService.findAllById(); // взял список типов компет
            model.addAttribute("ctypes", ctypeDtoList);
            model.addAttribute(COMPETA_VIEW_VARIABLE, competa.get()); // взяли в model
            return "competa-edit";
        } else {
            return REDIRECT_COMPETA;
        }
    }

    @PostMapping("/competa/{id}/edit") //
    public String competaUpdate(@PathVariable(value = "id") long id, @ModelAttribute CompetaDto competa) {
//        final List<IndustryDto> industryDtoList = industryService.findAllById(); // взял список индустрий
//        final List<CtypeDto> ctypeDtoList = ctypeService.findAllById(); // взял список типов компет
        competaService.update(id, competa);
        return REDIRECT_COMPETA;
    }

    @PostMapping("/competa/{id}/remove")
    public String competaDelete(@PathVariable(value = "id") long id) {
        competaService.remove(id);
        return REDIRECT_COMPETA;
    }
}
