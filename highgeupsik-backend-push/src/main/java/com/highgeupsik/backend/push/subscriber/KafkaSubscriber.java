package com.highgeupsik.backend.push.subscriber;

import com.highgeupsik.backend.core.events.CommentCreateEvent;
import com.highgeupsik.backend.core.model.notification.Subscription;
import com.highgeupsik.backend.push.fcm.FCMNotification;
import com.highgeupsik.backend.push.fcm.FCMSendService;
import com.highgeupsik.backend.push.service.SubscriptionQueryService;
import com.highgeupsik.backend.push.utils.ParsingUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaSubscriber {

    private final SubscriptionQueryService queryService;
    private final FCMSendService sendService;

    @KafkaListener(topics = "${spring.kafka.topic.comment-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void onCommentCreated(List<CommentCreateEvent> events) throws Exception {
        CommentCreateEvent commentCreateEvent = ParsingUtils.parseKafkaMessage(events.toString(),
            CommentCreateEvent[].class);
        Subscription subscription = queryService.findByUserId(commentCreateEvent.getReceiverId());
        FCMNotification notification = FCMNotification.ofNewComment(commentCreateEvent.getContent(),
            subscription.getToken());
        sendService.send(notification);
    }
}
