package com.competa.competademo.service.impl;

import com.competa.competademo.dto.CompetaDto;
import com.competa.competademo.dto.CtypeDto;
import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.entity.*;
import com.competa.competademo.exceptions.CompetaNotFoundException;
import com.competa.competademo.repository.CompetaRepository;
import com.competa.competademo.service.CompetaService;
import com.competa.competademo.service.CtypeService;
import com.competa.competademo.service.IndustryService;
import com.competa.competademo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrej Reutow
 * created on 03.08.2023
 */
@Service("CompetaService")
public class CompetaServiceImpl implements CompetaService<CompetaDto> {

    private final CompetaRepository competaRepository;
    private final UserService userService;
    private final IndustryService<IndustryDto> industryService;

    private final CtypeService<CtypeDto> ctypeService;

    public CompetaServiceImpl(CompetaRepository competaRepository,
                              UserService userService, IndustryService<IndustryDto> industryService, CtypeService<CtypeDto> ctypeService) {
        this.competaRepository = competaRepository;
        this.userService = userService;
        this.industryService = industryService;
        this.ctypeService = ctypeService;
    }
    @Transactional
    @Override
    public void addToAuthUser(CompetaDto competa) {
        final User authUser = userService.getAuthUser();
        // берем данные из БД про индустрию
        IndustryDto industryDto = industryService.findById(competa.getSelectedIndustryId()).orElseThrow(() -> new RuntimeException("Industry not found"));
        Industry industry = industryDto.toEntity();
        // берем данные из БД про тип компеты
        CtypeDto ctypeDto = ctypeService.findById(competa.getCtypeId()).orElseThrow(() -> new RuntimeException("Competa type not found"));
        Ctype ctype = ctypeDto.toEntity();
        // отправляем в компету параметры индустрии и ее типа
        Competa entity = competa.toEntity(industry, ctype);
        entity.setUser(authUser);
        entity = competaRepository.save(entity);
        authUser.getCompetas().add(entity);
        userService.saveUser(authUser);
    }
    @Transactional
    @Override
    public void update(final long competaId, final CompetaDto competa) {
        final Competa competaToEdit = findCompeta(competaId);
        competaToEdit.setTitle(competa.getTitle());
        competaToEdit.setDescription(competa.getDescription());
        competaToEdit.setDateOut(competa.getDateOut());
        competaToEdit.setStatus(competa.isStatus());
        // берем данные из БД про индустрию
        IndustryDto industryDto = industryService.findById(competa.getSelectedIndustryId()).orElseThrow(() -> new RuntimeException("Industry not found"));
        Industry industry = industryDto.toEntity();
        // берем данные из БД про тип компеты
        CtypeDto ctypeDto = ctypeService.findById(competa.getCtypeId()).orElseThrow(() -> new RuntimeException("Competa type not found"));
        Ctype ctype = ctypeDto.toEntity();
        // передаем в компету
        competaToEdit.setIndustry(industry);
        competaToEdit.setCtype(ctype);
        competaRepository.save(competaToEdit);
    }

    @Override
    public void remove(long competaId) {
        findCompeta(competaId);
        competaRepository.deleteById(competaId);
    }

    @Override
    public CompetaDto getById(long competaId) {
        return null;
    }

    @Override
    public Optional<CompetaDto> findById(long competaId) {
        return Optional.of(
                new CompetaDto(findCompeta(competaId))
        );
    }

    @Override
    public List<CompetaDto> findAllByAuthUser() {
        final User authUser = userService.getAuthUser();
        return competaRepository.findAllByUser(authUser)
                .stream()
                .map(CompetaDto::new)
                .toList();
    }

    @Override
    public int countByAuthUser() {
        final User authUser = userService.getAuthUser();
        return (int) competaRepository.countByUser(authUser);
    }

    @Override
    public void addCompetaImage(long competaId, ImageInfo competaImage) {
//        взять текущую Competa - как это сделать?
//        Обратись в сервис компеты, что бы найти компету по известному id
        Competa competa = findCompeta(competaId);
//        установить в ней значение setCompetaImage(competaImage);
        competa.setCompetaImage(competaImage);
//        сохранить в репозитории competaRepository(competa);
        competaRepository.save(competa);
    }

    @Override
    public String getCompetaImage(Competa competa) {
        final var filePath = Path.of(competa.getCompetaImage().getUrl());
        try {
            final UrlResource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                byte[] imageBytes = Files.readAllBytes(filePath);
                return Base64Utils.encodeToString(imageBytes);
            }
        } catch (final IOException e) {
            throw new RuntimeException("Error reading the avatar");
        }
        return "";
    }

    private Competa findCompeta(long competaId) {
        return competaRepository.findById(competaId)
                .orElseThrow(() -> new CompetaNotFoundException("Competa with id '{}' not exists", competaId));
    }
}
