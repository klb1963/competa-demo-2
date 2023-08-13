package com.competa.competademo.service.impl;

import com.competa.competademo.dto.CompetaDto;
import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.entity.Competa;
import com.competa.competademo.entity.User;
import com.competa.competademo.exceptions.CompetaNotFoundException;
import com.competa.competademo.repository.CompetaRepository;
import com.competa.competademo.service.CompetaService;
import com.competa.competademo.service.IndustryService;
import com.competa.competademo.service.UserService;
import org.springframework.stereotype.Service;

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

    public CompetaServiceImpl(CompetaRepository competaRepository,
                              UserService userService, IndustryService<IndustryDto> industryService) {
        this.competaRepository = competaRepository;
        this.userService = userService;
        this.industryService = industryService;
    }

    @Override
    public void addToAuthUser(CompetaDto competa) {
        final User authUser = userService.getAuthUser();
        IndustryDto industryDto = industryService.findById(competa.getSelectedIndustryId()).orElseThrow(() -> new RuntimeException("Competa not found"));

        Competa entity = competa.toEntity();
        entity.setUser(authUser);
        entity = competaRepository.save(entity);

        authUser.getCompetas().add(entity);
        userService.saveUser(authUser);
    }

    @Override
    public void update(final long competaId, final CompetaDto competa) {
        final Competa competaToEdit = findCompeta(competaId);
        competaToEdit.setTitle(competa.getTitle());
        competaToEdit.setDescription(competa.getDescription());
        competaToEdit.setDateOut(competa.getDateOut());
        competaToEdit.setStatus(competa.isStatus());
        // TODO - как получить industryId
    //    competaToEdit.setIndustry(competa.getSelectedIndustryId());
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

    private Competa findCompeta(long competaId) {
        return competaRepository.findById(competaId)
                .orElseThrow(() -> new CompetaNotFoundException("Competa with id '{}' not exists", competaId));
    }
}
