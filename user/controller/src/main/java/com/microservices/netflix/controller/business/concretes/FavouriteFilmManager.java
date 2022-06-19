package com.microservices.netflix.controller.business.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.FavouriteFilm;
import com.microservices.netflix.common.messages.user.favourite.FavouriteProcessMessage;
import com.microservices.netflix.common.messages.user.favourite.FavouriteProcessType;
import com.microservices.netflix.common.results.*;
import com.microservices.netflix.common.strings.ErrorMessages;
import com.microservices.netflix.common.strings.SuccessMessages;
import com.microservices.netflix.controller.business.abstracts.FavouriteFilmService;
import com.microservices.netflix.controller.dataAccess.FavouriteFilmDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

@Service
public class FavouriteFilmManager implements FavouriteFilmService {
    @Value("${ms.topic.favourite}")
    private String topic;
    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final FavouriteFilmDao favouriteFilmDao;

    @Autowired
    public FavouriteFilmManager(KafkaTemplate<String, String> kafkaTemplate, FavouriteFilmDao favouriteFilmDao) {
        this.kafkaTemplate = kafkaTemplate;
        this.favouriteFilmDao = favouriteFilmDao;
    }

    CustomStatusCodes statusCode = CustomStatusCodes.GENERAL_CATCH_ERROR;

    @Override
    public Result addToFav(FavouriteFilm favouriteFilm) {
        try {
            if (this.favouriteFilmDao.findByUserIdAndFilm(favouriteFilm.getUserId(), favouriteFilm.getFilm().getId()).isPresent()) {
                var getFilm = this.favouriteFilmDao.findByUserIdAndFilm(favouriteFilm.getUserId(), favouriteFilm.getFilm().getId()).get();
                if (getFilm.getFilm().getId() != null || getFilm.getUserId() != 0) {
                    String errorMessage = ErrorMessages.objectAlreadyExist;
                    statusCode = CustomStatusCodes.OBJECT_ALREADY_EXIST;
                    return new ErrorResult(errorMessage, statusCode.getValue());
                }
            }
            else {
                FavouriteProcessType type = FavouriteProcessType.ADD_TO_FAV;
                kafkaProducer(favouriteFilm, type);
            }
            return new SuccessResult(SuccessMessages.dataAdded, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorResult(e.toString(), statusCode.getValue());
        }
    }

    @Override
    public Result deleteFromFav(Long id) {
        try {
            var getFavFilm = this.favouriteFilmDao.findById(id).get();
            var checkFav = getFavFilm.getFilm().getId() == null || getFavFilm.getUserId() == 0;

            if (checkFav) {
                String errorMessage = ErrorMessages.objectNotFoundById;
                statusCode = CustomStatusCodes.OBJECT_NOT_FOUND;
                return new ErrorResult(errorMessage, statusCode.getValue());
            }
            FavouriteProcessType type = FavouriteProcessType.DELETE_FROM_FAV;
            kafkaProducer(id, type);
            return new SuccessResult(SuccessMessages.dataDeleted, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorResult(e.toString(), statusCode.getValue());
        }
    }

    @Override
    public DataResult<List<FavouriteFilm>> findFavouriteFilms() {
        try {
            return new SuccessDataResult<>(this.favouriteFilmDao.findAll(), SuccessMessages.allDataListed, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString(), CustomStatusCodes.DATA_NOT_LISTED.getValue());
        }
    }

    @Override
    public DataResult<List<FavouriteFilm>> findFavouriteFilmsByIsActiveAndUserId(int userId) {
        try {
            return new SuccessDataResult<>(this.favouriteFilmDao.findByIsActiveAndUserId(userId), SuccessMessages.allDataListed, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString(), CustomStatusCodes.DATA_NOT_LISTED.getValue());
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
