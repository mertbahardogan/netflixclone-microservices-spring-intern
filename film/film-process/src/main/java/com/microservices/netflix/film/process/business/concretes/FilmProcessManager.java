package com.microservices.netflix.film.process.business.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.common.messages.ProcessMessage;
import com.microservices.netflix.common.messages.ProcessType;
import com.microservices.netflix.film.process.business.abstracts.FilmProcessService;
import com.microservices.netflix.film.process.dataAccess.FilmProcessDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FilmProcessManager implements FilmProcessService {
    private final FilmProcessDao processDao;

    @Autowired
    public FilmProcessManager(FilmProcessDao processDao) {
        this.processDao = processDao;
    }

    @Override
    public void add(Film film) {
        System.out.println("ADD METHODU CALISTI!!!!!");
//        this.processDao.save(film);
    }

    @Override
    public void update(Film film, Long id) {

    }

    @Override
    public void delete(Long id) {

    }
}
