package com.microservices.netflix.common.service.services;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.common.results.SuccessDataResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "controller",url = "http://localhost/controller/api/")
@JsonDeserialize(as=Film.class)
public interface UserControllerClient {

    @RequestMapping("findAllByIsActive")
    SuccessDataResult<List<Film>> findAllByIsActive();
}
