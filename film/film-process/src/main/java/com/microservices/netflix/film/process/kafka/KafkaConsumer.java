package com.microservices.netflix.film.process.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.common.messages.ProcessMessage;
import com.microservices.netflix.common.messages.ProcessType;
import com.microservices.netflix.film.process.business.abstracts.FilmProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final FilmProcessService filmProcessService;

    @Autowired
    public KafkaConsumer(FilmProcessService filmProcessService) {
        this.filmProcessService = filmProcessService;
    }


    @KafkaListener(topics = "processTopic", groupId = "group_id")
    public void consume(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Film film = new Film();
        long id = 0;
        logger.info(String.format("$$$$ => Consumed message: %s", message));

        ProcessMessage processMessage = objectMapper.readValue(message, ProcessMessage.class);


        if (processMessage.getContent() instanceof LinkedHashMap) {
            Object obj = processMessage.getContent();
            film = objectMapper.convertValue(obj, Film.class);
        } else
        if (processMessage.getContent() instanceof Integer) {
            id = Long.valueOf(String.valueOf(processMessage.getContent()));
            logger.info(String.format("$$$$ =>GİRDİ VE GELEN CONTENT ID TİPİ: %s", id));
        } else {
            logger.info(String.format("ERROR FROM: Kafka Consumer"));
        }

        if (processMessage.getProcessType() == ProcessType.ADD) {
            this.filmProcessService.add(film);
        } else if (processMessage.getProcessType() == ProcessType.UPDATE) {
            this.filmProcessService.update(film.getId(), film);
        } else if (processMessage.getProcessType() == ProcessType.DELETE) {
            this.filmProcessService.deleteById(id);
        } else {
            System.out.println("ERROR FROM: Kafka Consumer!");
        }
    }
}
