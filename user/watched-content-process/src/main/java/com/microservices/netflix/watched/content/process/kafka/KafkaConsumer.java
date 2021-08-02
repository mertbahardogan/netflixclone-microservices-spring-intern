package com.microservices.netflix.watched.content.process.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.WatchContent;
import com.microservices.netflix.common.messages.user.watched.content.WatchedContentProcessMessage;
import com.microservices.netflix.watched.content.process.business.abstracts.WatchedFilmProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;


import java.util.LinkedHashMap;

@Service
public class KafkaConsumer {
    private final String TOPIC = "${ms.topic.content}";
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final WatchedFilmProcessService watchedFilmProcessService;

    @Autowired
    public KafkaConsumer(WatchedFilmProcessService watchedFilmProcessService) {
        this.watchedFilmProcessService = watchedFilmProcessService;
    }

    @KafkaListener(topics = TOPIC, groupId = "group_id")
    public void consume(String message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        WatchContent watchContent = new WatchContent();
        long id = 0;

        logger.info(String.format("$$$$ => Consumed message: %s", message));
        WatchedContentProcessMessage watchedContentProcessMessage = objectMapper.readValue(message, WatchedContentProcessMessage.class);
        logger.info(String.format("$$$$ => favouriteProcessMessage message: %s", watchedContentProcessMessage));


        if (watchedContentProcessMessage.getContent() instanceof LinkedHashMap) {
            Object obj = watchedContentProcessMessage.getContent();
            watchContent = objectMapper.convertValue(obj, WatchContent.class);
        } else {
            logger.info(String.format("ERROR FROM: WATCHED distrbuter Kafka Consumer"));
        }


        switch (watchedContentProcessMessage.getContentProcessType()) {
            case ADD:
                this.watchedFilmProcessService.add(watchContent);
                break;
            case UPDATE:
                this.watchedFilmProcessService.update(watchContent);
                break;
            default:
                System.out.println("ERROR FROM: WATCHED switch Kafka Consumer");
                break;
        }
    }


}
