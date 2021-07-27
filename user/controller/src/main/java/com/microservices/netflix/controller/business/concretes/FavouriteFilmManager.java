package com.microservices.netflix.controller.business.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.FavouriteFilm;
import com.microservices.netflix.common.messages.user.favourite.FavouriteProcessMessage;
import com.microservices.netflix.common.messages.user.favourite.FavouriteProcessType;
import com.microservices.netflix.common.results.*;
import com.microservices.netflix.common.strings.SuccessMessages;
import com.microservices.netflix.controller.business.abstracts.FavouriteFilmService;
import com.microservices.netflix.controller.dataAccess.FavouriteFilmDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

@Service
public class FavouriteFilmManager implements FavouriteFilmService {

    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final FavouriteFilmDao favouriteFilmDao;


    @Value("${ms.topic.favourite}")
    private String topic;

    @Autowired
    public FavouriteFilmManager(KafkaTemplate<String, String> kafkaTemplate, FavouriteFilmDao favouriteFilmDao) {
        this.kafkaTemplate = kafkaTemplate;
        this.favouriteFilmDao = favouriteFilmDao;
    }

    @Override
    public Result addToFav(FavouriteFilm favouriteFilm){
        try {
            FavouriteProcessType type = FavouriteProcessType.ADD_TO_FAV;
            kafkaProducer(favouriteFilm,type);
            return new SuccessResult(SuccessMessages.dataAdded);
        } catch (Exception e) {
            return new ErrorResult(e.toString());
        }
    }

    @Override
    public Result deleteFromFav(int id)  {
        try {
            FavouriteProcessType type = FavouriteProcessType.DELETE_FROM_FAV;
            kafkaProducer(id,type);
            return new SuccessResult(SuccessMessages.dataDeleted);
        } catch (Exception e) {
            return new ErrorResult(e.toString());
        }
    }

    @Override
    public DataResult<List<FavouriteFilm>> findFavouriteFilms() {
        try {
            return new SuccessDataResult<>(this.favouriteFilmDao.findAll(), SuccessMessages.allDataListed);
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString());
        }
    }

    @Override
    public DataResult<List<FavouriteFilm>> findFavouriteFilmsByIsActiveAndUserId(int userId) {
        try {
            return new SuccessDataResult<>(this.favouriteFilmDao.findByIsActiveAndUserId(userId), SuccessMessages.allDataListed);
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString());
        }
    }

    public void kafkaProducer(Object content, FavouriteProcessType type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        FavouriteProcessMessage<Object> processMessage = new FavouriteProcessMessage<>(
                type, content
        );

        var pm = objectMapper.writeValueAsString(processMessage);
        logger.info(String.format("$$$$ => Producing message: %s", pm));

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, pm);
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