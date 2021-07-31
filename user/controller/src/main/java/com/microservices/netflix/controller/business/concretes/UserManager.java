package com.microservices.netflix.controller.business.concretes;

import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.common.results.*;
import com.microservices.netflix.common.strings.SuccessMessages;
import com.microservices.netflix.controller.business.abstracts.UserService;
import com.microservices.netflix.controller.dataAccess.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserManager implements UserService {
    private final UserDao userDao;
    @Autowired
    public UserManager(UserDao userDao) {
        this.userDao = userDao;
    }

    CustomStatusCodes statusCode = CustomStatusCodes.DATA_NOT_LISTED;

    @Override
    public DataResult<List<Film>> findAllByIsActive() {
        try {
            return new SuccessDataResult<>(this.userDao.findAllByIsActive(), SuccessMessages.allDataListed, HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString(),statusCode.getValue());
        }
    }

    @Override
    public DataResult<Optional<Film>> findByIsActiveAndId(Long id) {
        try {
            return new SuccessDataResult<>(this.userDao.findByIsActiveAndId(id), SuccessMessages.dataListed,HttpStatus.OK.value());
        } catch (Exception e) {
            return new ErrorDataResult<>(e.toString(),statusCode.getValue());
        }
    }
}
