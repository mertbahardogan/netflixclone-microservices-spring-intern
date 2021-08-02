package com.microservices.netflix.controller.business.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.WatchContent;
import com.microservices.netflix.common.messages.user.watched.content.WatchedContentProcessMessage;
import com.microservices.netflix.common.messages.user.watched.content.WatchedContentProcessType;
import com.microservices.netflix.common.results.*;
import com.microservices.netflix.common.strings.ErrorMessages;
import com.microservices.netflix.common.strings.SuccessMessages;
import com.microservices.netflix.controller.business.abstracts.WatchedFilmService;
import com.microservices.netflix.controller.dataAccess.WatchedFilmDao;
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
public class WatchedFilmManager implements WatchedFilmService {
    @Value("${ms.topic.content}")
    private String topic;
    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final WatchedFilmDao watchedFilmDao;

    @Autowired
    public WatchedFilmManager(KafkaTemplate<String, String> kafkaTemplate, WatchedFilmDao watchedFilmDao) {
        this.kafkaTemplate = kafkaTemplate;
        this.watchedFilmDao = watchedFilmDao;
    }

    CustomStatusCodes statusCode = CustomStatusCodes.GENERAL_CATCH_ERROR;

    @Override
    public Result add(WatchContent watchContent) {
        try {
            if (this.watchedFilmDao.findByUserIdAndFilm(watchContent.getUserId(), watchContent.getFilm().getId()).isPresent()) {
                var getFilm = this.watchedFilmDao.findByUserIdAndFilm(watchContent.getUserId(), watchContent.getFilm().getId()).get();
                if (getFilm.getFilm().getId() != null || getFilm.getUserId() != 0) {
                    String errorMessage = ErrorMessages.objectAlreadyExist;
                    statusCode = CustomStatusCodes.OBJECT_ALREADY_EXIST;
                    return new ErrorResult(errorMessage, statusCode.getValue());
                }
            } else {
                WatchedContentProcessType type = WatchedContentProcessType.ADD;
                kafkaProducer(watchContent, type);
            }
            return new SuccessResult(SuccessMessages.dataAdded, HttpStatus.OK.value());

        } catch (Exception e) {
            return new ErrorResult(e.toString(), statusCode.getValue());
        }
    }

    @Override
    public Result update(Long id, WatchContent watchContent) {
        try {
            if (this.watchedFilmDao.findById(id).isPresent()) {
                WatchedContentProcessType type = WatchedContentProcessType.UPDATE;
                watchContent.setId(id);
                kafkaProducer(watchContent, type);
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
    public DataResult<List<WatchContent>> findByIsActiveAndIsFinishedAndUserId(int userId) {
        try {
            return new SuccessDataResult<>(this.watchedFilmDao.findByIsActiveAndIsFinishedAndUserId(userId), SuccessMessages.allDataListed, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString(), CustomStatusCodes.DATA_NOT_LISTED.getValue());
        }
    }

    @Override
    public DataResult<List<WatchContent>> findByIsActiveAndIsNotFinishedAndUserId(int userId) {
        try {
            return new SuccessDataResult<>(this.watchedFilmDao.findByIsActiveAndIsNotFinishedAndUserId(userId), SuccessMessages.allDataListed, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString(), CustomStatusCodes.DATA_NOT_LISTED.getValue());
        }
    }

    public void kafkaProducer(Object content, WatchedContentProcessType type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        WatchedContentProcessMessage<Object> processMessage = new WatchedContentProcessMessage<>(
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
