package com.microservices.netflix.film.controller.business.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.Film;


import com.microservices.netflix.common.messages.film.FilmProcessMessage;
import com.microservices.netflix.common.messages.film.FilmProcessType;
import com.microservices.netflix.common.results.*;
import com.microservices.netflix.common.service.services.UserControllerClient;
import com.microservices.netflix.common.strings.ErrorMessages;
import com.microservices.netflix.common.strings.SuccessMessages;
import com.microservices.netflix.film.controller.business.abstracts.FilmService;
import com.microservices.netflix.film.controller.business.helpers.FilmCheckHelper;
import com.microservices.netflix.film.controller.dataAccess.FilmDao;
import io.swagger.models.Xml;
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
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.Optional;

@Service
public class FilmManager implements FilmService {
    @Value("${ms.topic.process}")
    private String topic;
    CustomStatusCodes statusCode = CustomStatusCodes.GENERAL_CATCH_ERROR;
    private static final Logger logger = LoggerFactory.getLogger(FilmManager.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final FilmDao filmDao;

    private final UserControllerClient userControllerClient;


    @Autowired
    public FilmManager(KafkaTemplate<String, String> kafkaTemplate, FilmDao filmDao, UserControllerClient userControllerClient) {
        this.kafkaTemplate = kafkaTemplate;
        this.filmDao = filmDao;
        this.userControllerClient = userControllerClient;
    }


//    @Autowired
//    public FilmManager(KafkaTemplate<String, String> kafkaTemplate, FilmDao filmDao) {
//        this.kafkaTemplate = kafkaTemplate;
//        this.filmDao = filmDao;
//    }


    @Override
    public DataResult<List<Film>> findAll() {
        try {
            return new SuccessDataResult<>(this.filmDao.findAll(), SuccessMessages.allDataListed, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString(), CustomStatusCodes.DATA_NOT_LISTED.getValue());
        }
    }

    @Override
    public DataResult<Optional<Film>> findById(Long id) {
        try {
            return new SuccessDataResult<>(this.filmDao.findById(id), SuccessMessages.dataListed, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString(), CustomStatusCodes.DATA_NOT_LISTED.getValue());
        }
    }


    @Override
    public Result add(Film film) {

        try {
            var getFilm = this.filmDao.findByName(film.getName());
            var checkFields = !FilmCheckHelper.isFillFields(film);
            var checkFilm = getFilm.size() != 0;
            if (checkFields) {
                String errorMessage = "";
                if (checkFields) {
                    errorMessage = ErrorMessages.allFieldsRequired;
                    statusCode = CustomStatusCodes.ALL_FIELDS_REQUIRED;
                }
                if (checkFilm) {
                    errorMessage = ErrorMessages.objectAlreadyExist;
                    statusCode = CustomStatusCodes.OBJECT_ALREADY_EXIST;
                }
                return new ErrorResult(errorMessage, statusCode.getValue());
            }

            FilmProcessType type = FilmProcessType.ADD;
            kafkaProducer(film, type);
            return new SuccessResult(SuccessMessages.dataAdded, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorResult(e.toString(), statusCode.getValue());
        }
    }

    @Override
    public Result update(Long id, Film film) {
        try {
            var checkFields = !FilmCheckHelper.isFillFields(film);
            var getFilm = this.filmDao.findByName(film.getName());
            var checkFilm = getFilm.get(0).getId() == film.getId();
            var checkIsEmpty = getFilm.size() <= 1;

            if (checkFields || checkFilm) {
                String errorMessage = "";

                if (checkFields) {
                    errorMessage = ErrorMessages.allFieldsRequired;
                    statusCode = CustomStatusCodes.ALL_FIELDS_REQUIRED;
                }
                if (checkFilm) {
                    errorMessage = ErrorMessages.objectAlreadyExist;
                    statusCode = CustomStatusCodes.OBJECT_ALREADY_EXIST;
                }
//                if(isEmptyFilm){
//                    errorMessage=ErrorMessages.objectNotFoundByName;
//                    statusCode = CustomStatusCodes.OBJECT_NOT_FOUND;
//                }
                return new ErrorResult(errorMessage, statusCode.getValue());
            }

            FilmProcessType type = FilmProcessType.UPDATE;
            film.setId(id);
            kafkaProducer(film, type);
            return new SuccessResult(SuccessMessages.dataUpdated, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorResult(e.toString(), statusCode.getValue());
        }
    }

    @Override
    public Result delete(Long id) {
        try {
            var checkFilm = this.filmDao.findById(id).get().getId() != null; //Hata tetiklemesi için gerekli.
            FilmProcessType type = FilmProcessType.DELETE;
            kafkaProducer(id, type);
            return new SuccessResult(SuccessMessages.dataDeleted, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorResult(e.toString(), statusCode.getValue());
        }
    }

    @Override
    public Result setActive(Long id) {
        try {
            var getFilm = this.filmDao.findById(id).get();
            var checkIsPassive = getFilm.isActive() == true;
            var checkIsDeleted = getFilm.getDeleted() != null;
            if (checkIsPassive || checkIsDeleted) {
                String errorMessage = "";
                if (checkIsPassive) {
                    errorMessage = ErrorMessages.objectHasSameValue;
                    statusCode = CustomStatusCodes.OBJECT_HAS_SAME_VALUE;
                }
                if (checkIsDeleted) {
                    errorMessage = ErrorMessages.objectNotFoundById;
                    statusCode = CustomStatusCodes.OBJECT_NOT_FOUND;
                }
                return new ErrorResult(errorMessage, statusCode.getValue());
            }
            FilmProcessType type = FilmProcessType.SET_ACTIVE;
            kafkaProducer(id, type);
            return new SuccessResult(SuccessMessages.dataUpdated, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorResult(e.toString(), statusCode.getValue());
        }
    }

    @Override
    public Result setPassive(Long id) {
        try {
            var getFilm = this.filmDao.findById(id).get();
            var checkIsPassive = getFilm.isActive() == false;
            var checkIsDeleted = getFilm.getDeleted() != null;
            if (checkIsPassive || checkIsDeleted) {
                String errorMessage = "";
                if (checkIsPassive) {
                    errorMessage = ErrorMessages.objectHasSameValue;
                    statusCode = CustomStatusCodes.OBJECT_HAS_SAME_VALUE;
                }
                if (checkIsDeleted) {
                    errorMessage = ErrorMessages.objectNotFoundById;
                    statusCode = CustomStatusCodes.OBJECT_NOT_FOUND;
                }
                return new ErrorResult(errorMessage, statusCode.getValue());
            }
            FilmProcessType type = FilmProcessType.SET_PASSIVE;
            kafkaProducer(id, type);
            return new SuccessResult(SuccessMessages.dataUpdated, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorResult(e.toString(), statusCode.getValue());
        }
    }

    @Override
    public DataResult<List<Film>> findActiveFilmsFromUserService() {
        try{
            SuccessDataResult<List<Film>> listDataResult = this.userControllerClient.findAllByIsActive();

            System.out.println(listDataResult.getData().isEmpty()); //ArrayList
//            if (listDataResult.getCustomStatusCode() == 200) {
                return new SuccessDataResult<>(listDataResult.getData(), SuccessMessages.allDataListed, HttpStatus.OK.value());
//            } else {
//                return new ErrorDataResult<>("Data User'dan getirilirken ELSE'e düştü.", CustomStatusCodes.DATA_NOT_LISTED.getValue());
//            }
        }catch (Exception e){
            return new ErrorDataResult<>(e.toString(), CustomStatusCodes.DATA_NOT_LISTED.getValue());
        }
    }

    public void kafkaProducer(Object content, FilmProcessType type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FilmProcessMessage<Object> process = new FilmProcessMessage<>(
                type, content
        );

        var pm = objectMapper.writeValueAsString(process);
        logger.info(String.format("$$$$ => Producing message: %s", pm));

        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topic, pm);
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
