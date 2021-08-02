package com.microservices.netflix.controller.business.abstracts;

import com.microservices.netflix.common.entities.RateFilm;
import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RateFilmService {

    Result add(RateFilm rateFilm) ;

    Result update(Long id,RateFilm rateFilm);

    Result delete(Long id);

    DataResult<List<RateFilm>> findRatedFilmsByIsActiveAndUserId(int userId);

}
