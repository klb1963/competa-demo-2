package com.competa.competademo.repository;

import com.competa.competademo.entity.Industry;
import com.competa.competademo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndustryRepository extends JpaRepository<Industry, Long> {
    Optional<Industry> findByName(String name);
}
