package com.microservices.netflix.film.service.business.abstracts;


import com.microservices.netflix.film.service.entities.concretes.Film;

import java.util.List;

public interface FilmService {
    List<Film> getAll();
    Film getById(Long id);
}
