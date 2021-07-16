package com.microservices.netflix.film.controller.business.abstracts;

import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface FilmService {
    DataResult<List<Film>> findAll();

    DataResult<Optional<Film>> findById(Long id);

    Result add(Film film) throws IOException;

    Result update(Long id,Film film) throws IOException;

    Result delete(Long id) throws IOException;

}
