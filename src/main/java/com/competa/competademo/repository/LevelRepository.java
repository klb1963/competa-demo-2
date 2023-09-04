package com.competa.competademo.repository;

import com.competa.competademo.entity.Ctype;
import com.competa.competademo.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {

    Optional<Level> findByName(String name);

    @Query("select с from Level с order by с.id")
    List<Level> findAll();

}
