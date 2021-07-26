package com.microservices.netflix.rate.process.business.concretes;

import com.microservices.netflix.common.entities.RateFilm;
import com.microservices.netflix.rate.process.business.abstracts.RateProcessService;
import com.microservices.netflix.rate.process.dataAccess.RateProcessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateProcessManager implements RateProcessService {

    private final RateProcessDao rateProcessDao;

    @Autowired
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
    public void update(Long id, RateFilm rateFilm) {
        RateFilm value = this.rateProcessDao.findById(id).get();
        value.setRate(rateFilm.getRate());
        this.rateProcessDao.save(value);
    }
}
