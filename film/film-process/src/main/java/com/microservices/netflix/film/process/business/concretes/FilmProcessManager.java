package com.microservices.netflix.film.process.business.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.Film;
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
    private static final Logger logger = LoggerFactory.getLogger(FilmProcessManager.class);

    @Autowired
    public FilmProcessManager(FilmProcessDao processDao) {
        this.processDao = processDao;
    }

    @KafkaListener(topics = "kafkaTopic", groupId = "group_id")
    @Override
    public void consume(String message) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        logger.info(String.format("$$$$ => Consumed message: %s", message));

        var value = objectMapper.readerFor(Film.class).readValue(message);
        logger.info(String.format("$$$$ => Consumed message: %s", value));
    }

    @Override
    public void add(Film film) {
        this.processDao.add(film);
    }
}
