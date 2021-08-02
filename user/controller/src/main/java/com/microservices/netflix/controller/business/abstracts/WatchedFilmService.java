package com.microservices.netflix.controller.business.abstracts;

import com.microservices.netflix.common.entities.WatchContent;
import com.microservices.netflix.common.results.DataResult;
import com.microservices.netflix.common.results.Result;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface WatchedFilmService {
    Result add(WatchContent watchContent);

    Result update(Long id,WatchContent watchContent);

    DataResult<List<WatchContent>> findByIsActiveAndIsFinishedAndUserId(int userId);

    DataResult<List<WatchContent>> findByIsActiveAndIsNotFinishedAndUserId(int userId);

}
