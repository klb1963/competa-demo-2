package com.competa.competademo.service;

import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.entity.Industry;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface IndustryService<R> {

    @Transactional
    void saveIndustry(IndustryDto industry);

    Industry removeIndustry(long industryId);

    boolean isIndustryByNameExist(String industry);

    Optional<R> findById (final long industryId);

    List<R> findAllById();

    Industry saveIndustry(Industry industry);

    Industry findByName(String industry);
}
