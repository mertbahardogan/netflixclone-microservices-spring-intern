package com.microservices.netflix.film.controller.dataAccess;

import com.microservices.netflix.common.entities.Film;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmDao extends JpaRepository<Film, Long> {

    @Query("FROM Film WHERE deleted is NULL ORDER BY id")
    List<Film> findAll();

    Optional<Film> findById(Long id);

    List<Film> findByName(String name);

    @Query("FROM Film WHERE isActive = True AND deleted is NULL ORDER BY id")
    List<Film> findAllByIsActive();

    @Query("FROM Film WHERE isActive = False AND deleted is NULL ORDER BY id")
    List<Film> findAllByIsNotActive();

    @Query("FROM Film WHERE deleted is NOT NULL ORDER BY id")
    List<Film> findAllByDeletedIsNotNull();
}
