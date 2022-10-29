package com.highgeupsik.backend.push.subscribe;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Subscriber {

    @KafkaListener(topics = "${spring.kafka.topic.comment-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void onCommentCreated(String messages) throws Exception {
    }
}
