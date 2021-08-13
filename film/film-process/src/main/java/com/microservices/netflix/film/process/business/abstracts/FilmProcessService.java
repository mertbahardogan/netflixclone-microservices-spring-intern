package com.microservices.netflix.film.process.business.abstracts;

import com.microservices.netflix.common.entities.Film;
import org.springframework.stereotype.Service;

@Service
public interface FilmProcessService {
    Film add(Film film);

    Film update(Long id,Film film);

    void deleteById(Long id);

    void settingActive(Long id);

    void settingPassive(Long id);
}
