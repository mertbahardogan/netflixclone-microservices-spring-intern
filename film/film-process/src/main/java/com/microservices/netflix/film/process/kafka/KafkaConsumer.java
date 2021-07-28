package com.microservices.netflix.film.process.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.Film;
import com.microservices.netflix.common.messages.film.FilmProcessMessage;
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

        // GELEN MESAJIN DEĞERİNE GÖRE DESERIALIZE EDİLDİĞİ KOD BLOĞU
        if (filmProcessMessage.getContent() instanceof LinkedHashMap) {
            Object obj = filmProcessMessage.getContent();
            film = objectMapper.convertValue(obj, Film.class);
        } else if (filmProcessMessage.getContent() instanceof Integer) {
            id = Long.valueOf(String.valueOf(filmProcessMessage.getContent()));
        } else {
            logger.info(String.format("ERROR FROM: FILM deserialize Kafka Consumer"));
        }

        // GELEN MESAJIN TİPİNE GÖRE SERVİSE ATANDIĞI KOD BLOĞU
        switch (filmProcessMessage.getFilmProcessType()) {
            case ADD:
                this.filmProcessService.add(film);
                break;
            case UPDATE:
                this.filmProcessService.update(film.getId(), film);
                break;
            case DELETE:
                this.filmProcessService.deleteById(id);
                break;
            case SET_ACTIVE:
                this.filmProcessService.settingActive(id);
                break;
            case SET_PASSIVE:
                this.filmProcessService.settingPassive(id);
                break;
            default:
                System.out.println("ERROR FROM: FILM switch Kafka Consumer");
                break;
        }
    }
}
