package com.competa.competademo.repository;

import com.competa.competademo.entity.Competa;
import com.competa.competademo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetaRepository extends CrudRepository<Competa, Long> {
    long countByUser(User user);

    @Query("select с from Competa с where с.user = ?1")
    List<Competa> findAllByUser(User user);

}
