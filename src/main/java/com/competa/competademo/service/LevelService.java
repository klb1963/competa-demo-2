package com.competa.competademo.service;

import com.competa.competademo.dto.CtypeDto;
import com.competa.competademo.dto.LevelDto;
import com.competa.competademo.entity.Ctype;
import com.competa.competademo.entity.Level;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface LevelService<R> {
    @Transactional
    void saveLevel(LevelDto level);

    Level removeLevel(long levelId);

    boolean isLevelByNameExist(String level);

    Optional<R> findById (final long levelId);

    List<R> findAllById();

    Level saveLevel(Level level);

    Level findByName(String level);
}
