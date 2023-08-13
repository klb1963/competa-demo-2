package com.competa.competademo.service;

import com.competa.competademo.dto.CtypeDto;
import com.competa.competademo.dto.IndustryDto;
import com.competa.competademo.entity.Ctype;
import com.competa.competademo.entity.Industry;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface CtypeService<R> {

    @Transactional
    void saveCtype(CtypeDto ctype);

    Ctype removeCtype(long ctypeId);

    boolean isCtypeByNameExist(String ctype);

    Optional<R> findById (final long ctypeId);

    List<R> findAllById();

    Ctype saveCtype(Ctype ctype);

    Ctype findByName(String ctype);

}
