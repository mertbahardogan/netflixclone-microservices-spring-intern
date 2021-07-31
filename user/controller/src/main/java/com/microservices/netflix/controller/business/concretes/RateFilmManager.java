package com.microservices.netflix.controller.business.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.RateFilm;
import com.microservices.netflix.common.messages.user.favourite.FavouriteProcessType;
import com.microservices.netflix.common.messages.user.rate.RateProcessMessage;
import com.microservices.netflix.common.messages.user.rate.RateProcessType;
import com.microservices.netflix.common.results.*;
import com.microservices.netflix.common.strings.ErrorMessages;
import com.microservices.netflix.common.strings.SuccessMessages;
import com.microservices.netflix.controller.business.abstracts.RateFilmService;
import com.microservices.netflix.controller.dataAccess.RateFilmDao;
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
public class RateFilmManager implements RateFilmService {
    @Value("${ms.topic.rate}")
    private String topic;
    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RateFilmDao rateFilmDao;

    @Autowired
    public RateFilmManager(KafkaTemplate<String, String> kafkaTemplate, RateFilmDao rateFilmDao) {
        this.kafkaTemplate = kafkaTemplate;
        this.rateFilmDao = rateFilmDao;
    }

    CustomStatusCodes statusCode = CustomStatusCodes.GENERAL_CATCH_ERROR;

    @Override
    public Result add(RateFilm rateFilm) {
        try {
            if (this.rateFilmDao.findByUserIdAndFilm(rateFilm.getUserId(), rateFilm.getFilm().getId()).isPresent()) {
                var getFilm = this.rateFilmDao.findByUserIdAndFilm(rateFilm.getUserId(), rateFilm.getFilm().getId()).get();
                if (getFilm.getFilm().getId() != null || getFilm.getUserId() != 0) {
                    String errorMessage = ErrorMessages.objectAlreadyExist;
                    statusCode = CustomStatusCodes.OBJECT_ALREADY_EXIST;
                    return new ErrorResult(errorMessage, statusCode.getValue());
                }
            } else {
                RateProcessType type = RateProcessType.ADD;
                kafkaProducer(rateFilm, type);
            }
            return new SuccessResult(SuccessMessages.dataAdded, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorResult(e.toString(), statusCode.getValue());
        }
    }

    @Override
    public Result update(Long id, RateFilm rateFilm) {
        try {
            if (this.rateFilmDao.findById(id).isPresent()) {
                var getFilm = this.rateFilmDao.findById(id).get();
                RateProcessType type = RateProcessType.UPDATE;
                rateFilm.setId(id);
                kafkaProducer(rateFilm, type);
                return new SuccessResult(SuccessMessages.dataUpdated, HttpStatus.OK.value());
            } else {
                String errorMessage = ErrorMessages.objectNotFoundById;
                statusCode = CustomStatusCodes.OBJECT_NOT_FOUND;
                return new ErrorResult(errorMessage, statusCode.getValue());
            }

        } catch (Exception e) {
            return new ErrorResult(e.toString(), statusCode.getValue());
        }
    }

    @Override
    public Result delete(Long id) {
        try {
            var getFavFilm = this.rateFilmDao.findById(id).get();
            var checkFav = getFavFilm.getFilm().getId() == null || getFavFilm.getUserId() == 0;

            if (checkFav) {
                String errorMessage = ErrorMessages.objectNotFoundById;
                statusCode = CustomStatusCodes.OBJECT_NOT_FOUND;
                return new ErrorResult(errorMessage, statusCode.getValue());
            }
            RateProcessType type = RateProcessType.DELETE;
            kafkaProducer(id, type);
            return new SuccessResult(SuccessMessages.dataDeleted, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorResult(e.toString(), statusCode.getValue());
        }
    }

    @Override
    public DataResult<List<RateFilm>> findRatedFilmsByIsActiveAndUserId(int userId) {
        try {
            return new SuccessDataResult<>(this.rateFilmDao.findByIsActiveAndUserId(userId), SuccessMessages.allDataListed, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString(), CustomStatusCodes.DATA_NOT_LISTED.getValue());
        }
    }


    public void kafkaProducer(Object content, RateProcessType type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        RateProcessMessage<Object> processMessage = new RateProcessMessage<>(
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
