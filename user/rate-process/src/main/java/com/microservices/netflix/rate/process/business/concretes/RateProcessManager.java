package com.microservices.netflix.rate.process.business.concretes;

import com.microservices.netflix.common.entities.RateFilm;
import com.microservices.netflix.rate.process.business.abstracts.RateProcessService;
import com.microservices.netflix.rate.process.dataAccess.RateProcessDao;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Service;

@Service
public class RateProcessManager implements RateProcessService {

    private final RateProcessDao rateProcessDao;

    @ConstructorBinding
    public RateProcessManager(RateProcessDao rateProcessDao) {
        this.rateProcessDao = rateProcessDao;
    }


    @Override
    public void add(RateFilm rateFilm) {
        this.rateProcessDao.save(rateFilm);
    }

    @Override
    public void delete(Long id) {
        this.rateProcessDao.deleteById(id);
    }

    @Override
    public void update(@NotNull RateFilm rateFilm) {
        RateFilm value = this.rateProcessDao.findById(rateFilm.getId()).get();
        value.setRate(rateFilm.getRate());
        this.rateProcessDao.save(value);
    }
}
