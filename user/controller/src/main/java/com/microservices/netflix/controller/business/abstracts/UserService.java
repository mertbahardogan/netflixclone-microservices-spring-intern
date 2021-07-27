package com.microservices.netflix.controller.business.abstracts;

import com.microservices.netflix.common.entities.FavouriteFilm;
import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    DataResult<List<Film>> findAllByIsActive();

    DataResult<Optional<Film>> findByIsActiveAndId(Long id);


}
