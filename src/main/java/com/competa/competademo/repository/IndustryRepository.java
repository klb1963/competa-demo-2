package com.competa.competademo.repository;

import com.competa.competademo.entity.Competa;
import com.competa.competademo.entity.Industry;
import com.competa.competademo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IndustryRepository extends JpaRepository<Industry, Long> {
    Optional<Industry> findByName(String name);

    @Query("select с from Industry с order by с.name")
    List<Industry> findAll(Industry industry);
}
