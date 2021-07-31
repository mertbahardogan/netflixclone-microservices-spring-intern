package com.microservices.netflix.rate.process.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.entities.RateFilm;
import com.microservices.netflix.common.messages.user.rate.RateProcessMessage;
import com.microservices.netflix.rate.process.business.abstracts.RateProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final RateProcessService rateProcessService;
    private final String TOPIC = "${ms.topic.rate}";

    @Autowired
    public KafkaConsumer(RateProcessService rateProcessService) {
        this.rateProcessService = rateProcessService;
    }

    @KafkaListener(topics = TOPIC, groupId = "group_id")
    public void consume(String message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        RateFilm rateFilm = new RateFilm();
        long id = 0;

        logger.info(String.format("$$$$ => Consumed message: %s", message));
        RateProcessMessage rateProcessMessage = objectMapper.readValue(message, RateProcessMessage.class);
        logger.info(String.format("$$$$ => favouriteProcessMessage message: %s", rateProcessMessage));


        if (rateProcessMessage.getContent() instanceof LinkedHashMap) {
            Object obj = rateProcessMessage.getContent();
            rateFilm = objectMapper.convertValue(obj, RateFilm.class);
        } else if (rateProcessMessage.getContent() instanceof Integer) {
            id = Long.valueOf(String.valueOf(rateProcessMessage.getContent()));
        } else {
            logger.info(String.format("ERROR FROM: RATE distrbuter Kafka Consumer"));
        }


        switch (rateProcessMessage.getRateProcessType()) {
            case ADD:
                this.rateProcessService.add(rateFilm);
                break;
            case UPDATE:
                this.rateProcessService.update(rateFilm);
                break;
            case DELETE:
                this.rateProcessService.delete(id);
                break;
            default:
                System.out.println("ERROR FROM: RATE switch Kafka Consumer");
                break;
        }
    }
}
