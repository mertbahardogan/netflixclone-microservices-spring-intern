package com.microservices.netflix.rate.process.business.abstracts;

import com.microservices.netflix.common.entities.RateFilm;
import org.springframework.stereotype.Service;

@Service
public interface RateProcessService {
    void add(RateFilm rateFilm);

    void delete(Long id);

    void update(Long id, RateFilm rateFilm);
}
