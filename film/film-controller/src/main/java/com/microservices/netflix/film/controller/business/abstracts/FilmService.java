package com.microservices.netflix.film.controller.business.abstracts;

import com.microservices.netflix.common.entities.Film;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface FilmService {
    List<Film> findAll();

    Optional<Film> findById(Long id);

    void add(Film film) throws IOException;

    void update(Long id,Film film) throws IOException;

    void delete(Long id) throws IOException;

}
