package com.competa.competademo.service;

import java.util.List;
import java.util.Optional;

/**
 * @author Andrej Reutow
 * created on 03.08.2023
 */
public interface CompetaService<R> {

    void addToAuthUser(final R competa);

    void update(final long competaId, final R competa);

    void remove(final long competaId);

    R getById(final long competaId);

    Optional<R> findById(final long competaId);

    List<R> findAllByAuthUser();

    int countByAuthUser();
}
