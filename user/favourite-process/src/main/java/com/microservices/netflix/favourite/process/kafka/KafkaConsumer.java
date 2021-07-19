package com.microservices.netflix.favourite.process.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.FavouriteFilm;
import com.microservices.netflix.common.messages.user.FavouriteProcessMessage;
import com.microservices.netflix.common.messages.user.FavouriteProcessType;
import com.microservices.netflix.favourite.process.business.abstracts.FavouriteProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final FavouriteProcessService favouriteProcessService;
    private final String TOPIC = "${ms.topic.favourite}";


    @Autowired
    public KafkaConsumer(FavouriteProcessService favouriteProcessService) {
        this.favouriteProcessService = favouriteProcessService;
    }

    @KafkaListener(topics = TOPIC, groupId = "group_id")
    public void consume(String message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        FavouriteFilm favouriteFilm = new FavouriteFilm();
        long id = 0;

        logger.info(String.format("$$$$ => Consumed message: %s", message));
        FavouriteProcessMessage favouriteProcessMessage = objectMapper.readValue(message, FavouriteProcessMessage.class);
        logger.info(String.format("$$$$ => favouriteProcessMessage message: %s", favouriteProcessMessage));


        if (favouriteProcessMessage.getContent() instanceof LinkedHashMap) {
            Object obj = favouriteProcessMessage.getContent();
            favouriteFilm = objectMapper.convertValue(obj, FavouriteFilm.class);
        } else if (favouriteProcessMessage.getContent() instanceof Integer) {
            id = Long.valueOf(String.valueOf(favouriteProcessMessage.getContent()));
        } else {
            logger.info(String.format("ERROR FROM: FAV Kafka Consumer"));
        }

        if (favouriteProcessMessage.getFilmProcessType() == FavouriteProcessType.ADD_TO_FAV) {
            this.favouriteProcessService.addToFav(favouriteFilm);
        } else if (favouriteProcessMessage.getFilmProcessType() == FavouriteProcessType.DELETE_FROM_FAV) {
            this.favouriteProcessService.deleteFromFav(id);
        } else {
            System.out.println("ERROR FROM: Kafka Consumer!");
        }
    }
}
