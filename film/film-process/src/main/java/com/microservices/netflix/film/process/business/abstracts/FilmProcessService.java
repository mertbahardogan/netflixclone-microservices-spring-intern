package com.microservices.netflix.film.process.business.abstracts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.netflix.common.entities.Film;
import org.springframework.stereotype.Service;

@Service
public interface FilmProcessService {
    void add(Film film);

    void update(Film film,Long id);

    void delete(Long id);
}
