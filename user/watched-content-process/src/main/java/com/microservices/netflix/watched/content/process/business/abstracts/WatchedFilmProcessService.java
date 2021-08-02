package com.microservices.netflix.watched.content.process.business.abstracts;

import com.microservices.netflix.common.entities.WatchContent;
import org.springframework.stereotype.Service;

@Service
public interface WatchedFilmProcessService {

    void add(WatchContent watchContent);

    void update(WatchContent watchContent);
}
