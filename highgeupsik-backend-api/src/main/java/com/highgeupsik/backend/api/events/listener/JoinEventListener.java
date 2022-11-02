package com.highgeupsik.backend.api.events.listener;

import com.highgeupsik.backend.core.events.JoinEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class JoinEventListener {

    private final KafkaTemplate<String, JoinEvent> kafkaTemplate;

    @Value("v1.event.user-joined")
    private String topic;

    @Async
    @TransactionalEventListener
    public void handleUserJoinedEvent(JoinEvent joinEvent){
        kafkaTemplate.send(topic, joinEvent);
    }
}
