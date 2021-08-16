package com.microservices.netflix.film.process.business.abstracts;

import com.microservices.netflix.common.entities.Film;
import org.springframework.stereotype.Service;

@Service
public interface FilmProcessService {
    Film add(Film film);

    Film update(Film film);

    boolean deleteById(Long id);

    boolean settingActive(Long id);

    boolean settingPassive(Long id);
}
