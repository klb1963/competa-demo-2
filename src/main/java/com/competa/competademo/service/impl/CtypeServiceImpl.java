package com.competa.competademo.service.impl;

import com.competa.competademo.dto.CtypeDto;
import com.competa.competademo.entity.Ctype;
import com.competa.competademo.exceptions.CtypeNotFoundException;
import com.competa.competademo.repository.CtypeRepository;
import com.competa.competademo.service.CtypeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("CtypeService")
public class CtypeServiceImpl implements CtypeService {

    private final CtypeRepository ctypeRepository;

    public CtypeServiceImpl(CtypeRepository ctypeRepository) {
        this.ctypeRepository = ctypeRepository;
    }

    @Transactional
    @Override
    public void saveCtype(CtypeDto ctype) {
        Ctype entity = ctype.toEntity();
        entity = ctypeRepository.save(entity);
    }

    @Override
    public Ctype removeCtype(long ctypeId) {
        Ctype ctypeVictim = findCtypeById(ctypeId);
        ctypeRepository.delete(ctypeVictim);
        return ctypeVictim;
    }

    @Override
    public boolean isCtypeByNameExist(String ctype) {
        return ctypeRepository.findByName(ctype).isPresent();
    }

    @Override
    public Optional findById(long ctypeId) {
        return Optional.of(new CtypeDto(findCtypeById(ctypeId)));
    }

    @Override
    public List findAllById() {
        return ctypeRepository.findAll()
                .stream()
                .map(CtypeDto::new)
                .toList();
    }

    @Override
    public Ctype saveCtype(Ctype ctype) {
        return ctypeRepository.save(ctype);
    }

    @Override
    public Ctype findByName(String ctype) {
        return ctypeRepository.findByName(ctype).orElseThrow(() -> new CtypeNotFoundException(String.format("Competa Type with name '%s' not found", ctype)));
    }

    private Ctype findCtypeById (long ctypeId){
        return ctypeRepository.findById(ctypeId).orElseThrow(() -> new CtypeNotFoundException("Competa Type with id '{}' not exists", ctypeId));
    }

}
