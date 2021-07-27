package com.microservices.netflix.controller.business.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.RateFilm;
import com.microservices.netflix.common.messages.user.rate.RateProcessMessage;
import com.microservices.netflix.common.messages.user.rate.RateProcessType;
import com.microservices.netflix.common.results.*;
import com.microservices.netflix.common.strings.SuccessMessages;
import com.microservices.netflix.controller.business.abstracts.RateFilmService;
import com.microservices.netflix.controller.dataAccess.RateFilmDao;
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
public class RateFilmManager implements RateFilmService {

    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RateFilmDao rateFilmDao;

    @Value("${ms.topic.rate}")
    private String topic;

    @Autowired
    public RateFilmManager(KafkaTemplate<String, String> kafkaTemplate, RateFilmDao rateFilmDao) {
        this.kafkaTemplate = kafkaTemplate;
        this.rateFilmDao = rateFilmDao;
    }

    @Override
    public Result add(RateFilm rateFilm)  {
        try {
            RateProcessType type = RateProcessType.ADD;
            kafkaProducer(rateFilm,type);
            return new SuccessResult(SuccessMessages.dataAdded);
        } catch (Exception e) {
            return new ErrorResult(e.toString());
        }
    }

    @Override
    public Result update(Long id, RateFilm rateFilm)  {
        try {
            RateProcessType type = RateProcessType.UPDATE;
            rateFilm.setId(id);
            kafkaProducer(rateFilm,type);
            return new SuccessResult(SuccessMessages.dataUpdated);
        } catch (Exception e) {
            return new ErrorResult(e.toString());
        }
    }

    @Override
    public Result delete(Long id)  {
        try {
            RateProcessType type = RateProcessType.DELETE;
            kafkaProducer(id,type);
            return new SuccessResult(SuccessMessages.dataDeleted);
        } catch (Exception e) {
            return new ErrorResult(e.toString());
        }
    }

    @Override
    public DataResult<List<RateFilm>> findRatedFilmsByIsActiveAndUserId(int userId) {
        try {
            return new SuccessDataResult<>(this.rateFilmDao.findByIsActiveAndUserId(userId), SuccessMessages.allDataListed);
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString());
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
