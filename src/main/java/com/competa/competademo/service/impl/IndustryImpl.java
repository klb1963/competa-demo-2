package com.competa.competademo.service.impl;

import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.entity.Industry;
import com.competa.competademo.exceptions.IndustryNotFoundException;
import com.competa.competademo.repository.IndustryRepository;
import com.competa.competademo.service.IndustryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("IndustryService")
public class IndustryImpl implements IndustryService {

    private final IndustryRepository industryRepository;

    public IndustryImpl(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    @Transactional
    @Override
    public void saveIndustry(IndustryDto industry) {
        Industry entity = industry.toEntity();
        entity = industryRepository.save(entity);
    }

    @Transactional
    @Override
    public Industry removeIndustry(String industry) {
        Industry industryVictim = findByName(industry);
        industryRepository.delete(industryVictim);
        return industryVictim;
    }

    @Override
    public boolean isIndustryByNameExist(String industry) {
        return industryRepository.findByName(industry).isPresent();
    }

    @Override
    public Industry findById(long id) {
        return industryRepository.findById(id).orElseThrow(() -> new IndustryNotFoundException("Industry with id '{}' not found", id));
    }

    @Override
    public List<IndustryDto> findAllIndustries() {
        return industryRepository.findAll().stream().map(IndustryDto::new).toList();
    }

    @Override
    public Industry saveIndustry(Industry industry) {
        return industryRepository.save(industry);
    }

    @Override
    public Industry findByName(String industry) {
        return industryRepository.findByName(industry).orElseThrow(() -> new IndustryNotFoundException(String.format("Industry with name '%s' not found", industry)));
    }
}
