package com.microservices.netflix.film.process.business.concretes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.common.messages.ProcessMessage;
import com.microservices.netflix.common.messages.ProcessType;
import com.microservices.netflix.film.process.business.abstracts.ConsumerService;
import com.microservices.netflix.film.process.business.abstracts.FilmProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerManager implements ConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(FilmProcessManager.class);
   private final FilmProcessService filmProcessService;

    @Autowired
    public ConsumerManager(@Qualifier("filmProcessService") FilmProcessService filmProcessService) {
        this.filmProcessService = filmProcessService;
    }


    @KafkaListener(topics = "kafkaTopic", groupId = "group_id")
    @Override
    public void consume(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        logger.info(String.format("$$$$ => Consumed message: %s", message));


        ProcessMessage processMessage = objectMapper.readValue(message, ProcessMessage.class);
        logger.info(String.format("$$$$ => Consumed message processType: %s", processMessage.getProcessType()));
        logger.info(String.format("$$$$ => Consumed message processType: %s", processMessage.getContent()));
        Film emp = objectMapper.readValue(processMessage.getContent().toString(), Film.class);
        if (processMessage.getProcessType() == ProcessType.ADD) {
            this.filmProcessService.add(emp);
            System.out.println("ADD'E GİTMEK ÜZRE HAZIR.");
        }
    }
}
