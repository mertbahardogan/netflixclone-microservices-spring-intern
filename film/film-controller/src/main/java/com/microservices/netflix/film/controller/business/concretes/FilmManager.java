package com.microservices.netflix.film.controller.business.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.Film;


import com.microservices.netflix.common.messages.film.FilmProcessMessage;
import com.microservices.netflix.common.messages.film.FilmProcessType;
import com.microservices.netflix.common.results.*;
import com.microservices.netflix.common.strings.SuccessMessages;
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
    private final String TOPIC = "crudProcessTopic";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final FilmDao filmDao;

    @Autowired
    public FilmManager(KafkaTemplate<String, String> kafkaTemplate, FilmDao filmDao) {
        this.kafkaTemplate = kafkaTemplate;
        this.filmDao = filmDao;
    }

    @Override
    public DataResult<List<Film>> findAll() {
        try {
            return new SuccessDataResult<List<Film>>(this.filmDao.findAll(), SuccessMessages.allDataListed);
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString());
        }
    }

    @Override
    public DataResult<Optional<Film>> findById(Long id) {
        try {
            return new SuccessDataResult<Optional<Film>>(this.filmDao.findById(id), SuccessMessages.dataListed);
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString());
        }
    }


    @Override
    public Result add(Film film) {
        try {
            FilmProcessType type = FilmProcessType.ADD;
            kafkaProducer(film, type);
            return new SuccessResult(SuccessMessages.dataAdded);
        } catch (Exception e) {
            return new ErrorResult(e.toString());
        }
    }

    @Override
    public Result update(Long id, Film film) {
        try {
            FilmProcessType type = FilmProcessType.UPDATE;
            film.setId(id);
            kafkaProducer(film, type);
            return new SuccessResult(SuccessMessages.dataUpdated);
        } catch (Exception e) {
            return new ErrorResult(e.toString());
        }
    }

    @Override
    public Result delete(Long id) {
        try {
            FilmProcessType type = FilmProcessType.DELETE;
            kafkaProducer(id, type);
            return new SuccessResult(SuccessMessages.dataDeleted);
        } catch (Exception e) {
            return new ErrorResult(e.toString());
        }
    }

    public void kafkaProducer(Object content, FilmProcessType type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FilmProcessMessage<Object> process = new FilmProcessMessage<>(
                type, content
        );

        var pm = objectMapper.writeValueAsString(process);
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(TOPIC, pm);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onFailure(Throwable ex) {
                logger.info("Unable to send message=[ {} ] due to : {}", pm, ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Sent message=[ {} ] with offset=[ {} ]", pm, result.getRecordMetadata().offset());
            }
        });
    }
}

//        logger.info(String.format("$$$$ => Producing message: %s", pm));
//                logger.info(String.format("$$$$ =>TOPICCCC: %s", TOPIC));