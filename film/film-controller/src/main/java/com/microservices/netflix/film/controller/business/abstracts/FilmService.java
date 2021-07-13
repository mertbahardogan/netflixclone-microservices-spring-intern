package com.microservices.netflix.film.controller.business.abstracts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.netflix.common.entities.Film;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public interface FilmService {
    List<Film> findAll();

    Optional<Film> findById(Long id);

    void add(Film film) throws JsonProcessingException;
}
