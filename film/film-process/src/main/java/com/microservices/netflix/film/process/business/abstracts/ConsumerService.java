package com.microservices.netflix.film.process.business.abstracts;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface ConsumerService {
    void consume(String message) throws JsonProcessingException;
}
