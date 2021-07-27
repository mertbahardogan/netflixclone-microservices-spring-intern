package com.microservices.netflix.controller.business.abstracts;

import com.microservices.netflix.common.entities.RateFilm;
import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface RateFilmService {

    Result add(RateFilm rateFilm) throws IOException;

    Result update(Long id,RateFilm rateFilm) throws IOException;

    Result delete(Long id) throws IOException;

    DataResult<List<RateFilm>> findRatedFilmsByIsActiveAndUserId(int userId);

}
