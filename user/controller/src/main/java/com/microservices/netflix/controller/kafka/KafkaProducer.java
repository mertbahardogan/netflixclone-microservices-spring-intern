package com.microservices.netflix.controller.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.netflix.common.messages.user.FavouriteProcessMessage;
import com.microservices.netflix.common.messages.user.FavouriteProcessType;
import com.microservices.netflix.controller.business.concretes.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);
    static KafkaTemplate<String, String> kafkaTemplate = null;
    @Value("${ms.topic.favourite}")
    private static String topic;

//    @Autowired
//    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }


    public static void kafkaProducer(Object content, FavouriteProcessType type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FavouriteProcessMessage<Object> processMessage = new FavouriteProcessMessage<>(
                type, content
        );

        var pm = objectMapper.writeValueAsString(processMessage);
        logger.info(String.format("$$$$ => Producing message: %s", pm));

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, pm);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onFailure(Throwable ex) {
                logger.info("Unable to send message=[ {} ] due to : {}", pm, ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Sent message=[ {} ] with offset=[ {} ]", pm, result.getRecordMetadata().offset());
            }
        });
    }
}
