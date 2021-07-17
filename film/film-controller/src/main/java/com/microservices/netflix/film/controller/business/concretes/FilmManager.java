package com.microservices.netflix.film.controller.business.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.Film;


import com.microservices.netflix.common.messages.ProcessMessage;
import com.microservices.netflix.common.messages.ProcessType;
import com.microservices.netflix.common.results.*;
import com.microservices.netflix.film.controller.business.abstracts.FilmService;
import com.microservices.netflix.film.controller.dataAccess.FilmDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.Optional;

@Service
@PropertySource("classpath:application.properties")
public class FilmManager implements FilmService {
    private static final Logger logger = LoggerFactory.getLogger(FilmManager.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC = "processTopic"; //hata var //"${ms.topic.process}"
    private final String TOPIC_DENE = "ms.topic.process";

    private final FilmDao filmDao;

    @Autowired
    public FilmManager(KafkaTemplate<String, String> kafkaTemplate, FilmDao filmDao) {
        this.kafkaTemplate = kafkaTemplate;
        this.filmDao = filmDao;
    }

    @Override
    public DataResult<List<Film>> findAll() {
        try {
            return new SuccessDataResult<List<Film>>(this.filmDao.findAll(), "Başarıyla listelendi.");
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString());
        }
    }

    @Override
    public DataResult<Optional<Film>> findById(Long id) {
        try {
            return new SuccessDataResult<Optional<Film>>(this.filmDao.findById(id), "Başarıyla listelendi.");
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString());
        }
    }


    @Override
    public Result add(Film film) {
        try {
            ProcessType type = ProcessType.ADD;
            kafkaProducer(film, type);
            return new SuccessResult("Ekleme işlemi başarılı.");
        } catch (Exception e) {
            return new ErrorResult(e.toString());
        }
    }

    @Override
    public Result update(Long id, Film film) {
        try {
            ProcessType type = ProcessType.UPDATE;
            film.setId(id);
            kafkaProducer(film, type);
            return new SuccessResult("Güncelleme işlemi başarılı.");
        } catch (Exception e) {
            return new ErrorResult(e.toString());
        }
    }

    @Override
    public Result delete(Long id) {
        try {
            ProcessType type = ProcessType.DELETE;
            kafkaProducer(id, type);
            return new SuccessResult("Silme işlemi başarılı.");
        } catch (Exception e) {
            return new ErrorResult(e.toString());
        }
    }

    public void kafkaProducer(Object content, ProcessType type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProcessMessage<Object> process = new ProcessMessage<>(
                type, content
        );

        var pm = objectMapper.writeValueAsString(process);
        logger.info(String.format("$$$$ => Producing message: %s", pm));
        logger.info(String.format("$$$$ =>TOPICCCC: %s", TOPIC_DENE));

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

