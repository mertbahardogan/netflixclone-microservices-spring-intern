package com.microservices.netflix.film.controller.business.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.Film;


import com.microservices.netflix.common.messages.ProcessMessage;
import com.microservices.netflix.common.messages.ProcessType;
import com.microservices.netflix.film.controller.business.abstracts.FilmService;
import com.microservices.netflix.film.controller.dataAccess.FilmDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.Optional;

@Service
public class FilmManager implements FilmService {
    private static final Logger logger = LoggerFactory.getLogger(FilmManager.class);
    private final KafkaTemplate<String, ProcessMessage> kafkaTemplate;
    private final String TOPIC = "kafkaTopic";

    private final FilmDao filmDao;

    @Autowired
    public FilmManager(KafkaTemplate<String, ProcessMessage> kafkaTemplate, FilmDao filmDao) {
        this.kafkaTemplate = kafkaTemplate;
        this.filmDao = filmDao;
    }

    @Override
    public List<Film> findAll() {
        return this.filmDao.findAll();
    }

    @Override
    public Optional<Film> findById(Long id) {
        return this.filmDao.findById(id);
    }


    @Override
    public void add(Film film) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        //Object filmJson=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(film);
        Object filmJson = objectMapper.writeValueAsString(film);
        ProcessMessage<String> pm = new ProcessMessage<>(
                ProcessType.ADD,
                filmJson.toString()
        );

        logger.info(String.format("$$$$ => Producing message: %s", film.getName()));
        logger.info(filmJson.toString());
        ListenableFuture<SendResult<String, ProcessMessage>> future = this.kafkaTemplate.send(TOPIC, pm);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onFailure(Throwable ex) {
                logger.info("Unable to send message=[ {} ] due to : {}", pm, ex.getMessage());

            }

            @Override
            public void onSuccess(SendResult<String, ProcessMessage> result) {
                logger.info("Sent message=[ {} ] with offset=[ {} ]", pm, result.getRecordMetadata().offset());
            }
        });
    }
}
