package com.competa.competademo.service.impl;

import com.competa.competademo.dto.CompetaDto;
import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.entity.Industry;
import com.competa.competademo.entity.User;
import com.competa.competademo.exceptions.IndustryNotFoundException;
import com.competa.competademo.repository.IndustryRepository;
import com.competa.competademo.service.IndustryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("IndustryService")
public class IndustryImpl implements IndustryService {

    // поле
    private final IndustryRepository industryRepository;

    // конструктор
    public IndustryImpl(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    // сохраняем Industry в БД
    @Transactional
    @Override
    public void saveIndustry(IndustryDto industry) {
        Industry entity = industry.toEntity();
        entity = industryRepository.save(entity);
    }

    // удаляем Industry из БД
    @Transactional
    @Override
    public Industry removeIndustry(long industryId) {
        Industry industryVictim = findIndustryById(industryId);
        industryRepository.delete(industryVictim);
        return industryVictim;
    }

    // проверка на наличие в репозитории
    @Override
    public boolean isIndustryByNameExist(String industry) {
        return industryRepository.findByName(industry).isPresent();
    }

    // поиск индустрии через класс Optional
    @Override
    public Optional<IndustryDto> findById(long industryId) {
        return Optional.of(new IndustryDto(findIndustryById(industryId))); // вызов метода, который внизу
    }

    //получение списка Industry из БД
    @Override
    public List<IndustryDto> findAllById() {
        return industryRepository.findAll()
                .stream()
                .map(IndustryDto::new)
                .toList();
    }

    // сохранение в репозитории
    @Override
    public Industry saveIndustry(Industry industry) {
        return industryRepository.save(industry);
    }

    @Override
    public Industry findByName(String industry) {
        return industryRepository.findByName(industry).orElseThrow(() -> new IndustryNotFoundException(String.format("Industry with name '%s' not found", industry)));
    }

    private Industry findIndustryById (long industryId){
        return industryRepository.findById(industryId).orElseThrow(() -> new IndustryNotFoundException("Industry with id '{}' not exists", industryId));
    }

}
