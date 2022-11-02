package com.highgeupsik.backend.push.subscriber;

import static com.highgeupsik.backend.push.utils.ParsingUtils.parseKafkaMessage;

import com.highgeupsik.backend.core.events.CommentCreateEvent;
import com.highgeupsik.backend.core.events.JoinEvent;
import com.highgeupsik.backend.core.model.notification.Subscription;
import com.highgeupsik.backend.push.fcm.FCMNotification;
import com.highgeupsik.backend.push.fcm.FCMSendService;
import com.highgeupsik.backend.push.service.MailService;
import com.highgeupsik.backend.push.service.SubscriptionQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaSubscriber {

    private final SubscriptionQueryService queryService;
    private final FCMSendService fcmSendService;
    private final MailService mailService;

    @KafkaListener(topics = "${spring.kafka.topic.comment-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void onCommentCreated(List<CommentCreateEvent> events) {
        CommentCreateEvent event = parseKafkaMessage(events.toString(), CommentCreateEvent[].class);
        Subscription subscription = queryService.findByUserId(event.getReceiverId());
        FCMNotification notification = FCMNotification.ofNewComment(event.getContent(),
            subscription.getToken());
        fcmSendService.send(notification);
    }

    @KafkaListener(topics = "${spring.kafka.topic.user-joined}", groupId = "${spring.kafka.consumer.group-id}")
    public void onUserJoined(List<JoinEvent> events) {
        JoinEvent event = parseKafkaMessage(events.toString(), JoinEvent[].class);
        mailService.sendEmail(event);
    }
}
