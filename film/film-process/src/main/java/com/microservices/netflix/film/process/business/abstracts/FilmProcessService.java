package com.microservices.netflix.film.process.business.abstracts;

import com.microservices.netflix.common.entities.Film;
import org.springframework.stereotype.Service;

@Service
public interface FilmProcessService {
    void add(Film film);

    void update(Long id,Film film);

    void deleteById(Long id);

    void setActive(Long id);

    void setPassive(Long id);
}
