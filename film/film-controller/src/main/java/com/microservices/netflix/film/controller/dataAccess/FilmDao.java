package com.microservices.netflix.film.controller.dataAccess;

import com.microservices.netflix.common.entities.Film;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmDao extends JpaRepository<Film, Long> {

    List<Film> findAll();

    Optional<Film> findById(Long id);

    List<Film> findByName(String name);
}
