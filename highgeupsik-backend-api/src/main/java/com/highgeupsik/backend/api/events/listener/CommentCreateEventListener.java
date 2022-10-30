package com.highgeupsik.backend.api.events.listener;

import com.highgeupsik.backend.core.events.CommentCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class CommentCreateEventListener {

    private final KafkaTemplate<String, CommentCreateEvent> kafkaTemplate;

    @Value("v1.event.comment-created")
    private String topic;

    @Async
    @TransactionalEventListener
    public void handleCommentCreateEvent(CommentCreateEvent commentCreateEvent) {
        kafkaTemplate.send(topic, commentCreateEvent);
    }
}
