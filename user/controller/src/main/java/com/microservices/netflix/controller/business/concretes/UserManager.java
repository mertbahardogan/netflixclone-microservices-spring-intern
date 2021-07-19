package com.microservices.netflix.controller.business.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.common.messages.film.FilmProcessMessage;
import com.microservices.netflix.common.messages.film.FilmProcessType;
import com.microservices.netflix.common.messages.user.FavouriteProcessMessage;
import com.microservices.netflix.common.messages.user.FavouriteProcessType;
import com.microservices.netflix.common.results.*;
import com.microservices.netflix.common.strings.SuccessMessages;
import com.microservices.netflix.controller.business.abstracts.UserService;
import com.microservices.netflix.controller.dataAccess.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserManager implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);
    private final String TOPIC = "favProcessTopic";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private UserDao userDao;

    @Autowired
    public UserManager(KafkaTemplate<String, String> kafkaTemplate, UserDao userDao) {
        this.kafkaTemplate = kafkaTemplate;
        this.userDao = userDao;
    }

    @Override
    public DataResult<List<Film>> findAllByIsActive() {
        try {
            return new SuccessDataResult<List<Film>>(this.userDao.findAllByIsActive(), SuccessMessages.allDataListed);
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString());
        }
    }

    @Override
    public DataResult<Optional<Film>> findByIsActiveAndId(Long id) {
        try {
            return new SuccessDataResult<Optional<Film>>(this.userDao.findByIsActiveAndId(id), SuccessMessages.dataListed);
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString());
        }
    }

    @Override
    public Result addToFav(int userId, int filmId) throws IOException {
        try {
            FavouriteProcessType type = FavouriteProcessType.ADD_TO_FAV;
            kafkaProducer(userId, filmId, type);
            return new SuccessResult(SuccessMessages.dataAdded);
        } catch (Exception e) {
            return new ErrorResult(e.toString());
        }
    }

    @Override
    public Result deleteFromFav(int userId, int filmId) throws IOException {
        return null;
    }

    public void kafkaProducer(Object content, Object subContent, FavouriteProcessType type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FavouriteProcessMessage<Object> processMessage = new FavouriteProcessMessage<>(
                type, content, subContent
        );

        var pm = objectMapper.writeValueAsString(processMessage);
        logger.info(String.format("$$$$ => Producing message: %s", pm));
        logger.info(String.format("$$$$ =>TOPICCCC: %s", TOPIC));

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
