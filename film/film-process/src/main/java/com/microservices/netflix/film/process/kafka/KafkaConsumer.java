package com.microservices.netflix.film.process.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.common.messages.ProcessMessage;
import com.microservices.netflix.common.messages.ProcessType;
import com.microservices.netflix.film.process.business.abstracts.FilmProcessService;
import com.microservices.netflix.film.process.business.concretes.FilmProcessManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final FilmProcessService filmProcessService;

    public KafkaConsumer(FilmProcessService filmProcessService) {
        this.filmProcessService = filmProcessService;
    }


    @KafkaListener(topics = "kafkaTopic", groupId = "group_id")
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
