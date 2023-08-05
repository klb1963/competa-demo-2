package com.competa.competademo.service;

import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.entity.Industry;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IndustryService {

    @Transactional
    void saveIndustry(IndustryDto industry);

    Industry removeIndustry(String industry);

    boolean isIndustryByNameExist(String industry);

    Industry findById(long id);

    List<IndustryDto> findAllIndustries();

    Industry saveIndustry(Industry industry);

    Industry findByName(String industry);
}
