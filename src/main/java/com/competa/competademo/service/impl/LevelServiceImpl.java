package com.competa.competademo.service.impl;

import com.competa.competademo.dto.CtypeDto;
import com.competa.competademo.dto.LevelDto;
import com.competa.competademo.entity.Ctype;
import com.competa.competademo.entity.Level;
import com.competa.competademo.exceptions.CtypeNotFoundException;
import com.competa.competademo.exceptions.LevelNotFoundException;
import com.competa.competademo.repository.LevelRepository;
import com.competa.competademo.service.LevelService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("LevelService")
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;

    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Transactional
    @Override
    public void saveLevel(LevelDto level) {
        Level entity = level.toEntity();
        entity = levelRepository.save(entity);
    }
    @Override
    public Level removeLevel(long levelId) {
        Level levelVictim = findLevelById(levelId);
        levelRepository.delete(levelVictim);
        return levelVictim;
    }

    @Override
    public boolean isLevelByNameExist(String level) {
        return levelRepository.findByName(level).isPresent();
    }

    @Override
    public Optional findById(long levelId) {
        return Optional.of(new LevelDto(findLevelById(levelId)));
    }

    @Override
    public List findAllById() {
        return levelRepository.findAll()
                .stream()
                .map(LevelDto::new)
                .toList();
    }

    @Override
    public Level saveLevel(Level level) {
        return levelRepository.save(level);
    }

    @Override
    public Level findByName(String level) {
        return levelRepository.findByName(level).orElseThrow(() -> new LevelNotFoundException(String.format("Level with name '%s' not found", level)));
    }

    private Level findLevelById (long levelId){
        return levelRepository.findById(levelId).orElseThrow(() -> new LevelNotFoundException("Level with id '{}' not exists", levelId));
    }

}
