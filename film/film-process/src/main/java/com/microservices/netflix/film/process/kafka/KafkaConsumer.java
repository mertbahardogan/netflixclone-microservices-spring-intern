package com.microservices.netflix.film.process.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.common.messages.film.FilmProcessMessage;
import com.microservices.netflix.common.messages.film.FilmProcessType;
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
    private final String TOPIC = "${ms.topic.process}";


    @Autowired
    public KafkaConsumer(FilmProcessService filmProcessService) {
        this.filmProcessService = filmProcessService;
    }


    @KafkaListener(topics = TOPIC, groupId = "group_id")
    public void consume(String message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Film film = new Film();
        long id = 0;

        logger.info(String.format("$$$$ => Consumed message: %s", message));
        FilmProcessMessage filmProcessMessage = objectMapper.readValue(message, FilmProcessMessage.class);


        if (filmProcessMessage.getContent() instanceof LinkedHashMap) {
            Object obj = filmProcessMessage.getContent();
            film = objectMapper.convertValue(obj, Film.class);
        } else if (filmProcessMessage.getContent() instanceof Integer) {
            id = Long.valueOf(String.valueOf(filmProcessMessage.getContent()));
        } else {
            logger.info(String.format("ERROR FROM: Kafka Consumer"));
        }

        if (filmProcessMessage.getFilmProcessType() == FilmProcessType.ADD) {
            this.filmProcessService.add(film);
        } else if (filmProcessMessage.getFilmProcessType() == FilmProcessType.UPDATE) {
            this.filmProcessService.update(film.getId(), film);
        } else if (filmProcessMessage.getFilmProcessType() == FilmProcessType.DELETE) {
            this.filmProcessService.deleteById(id);
        } else {
            System.out.println("ERROR FROM: Kafka Consumer!");
        }
    }
}
