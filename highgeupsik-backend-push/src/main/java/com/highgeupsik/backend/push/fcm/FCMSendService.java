package com.highgeupsik.backend.push.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FCMSendService {

    public void send(FCMNotification notification) throws ExecutionException, InterruptedException {
        Message message = Message.builder()
            .setToken(notification.getToken())
            .setWebpushConfig(
                WebpushConfig.builder()
                    .putHeader("ttl", "300")
                    .setNotification(new WebpushNotification(notification.getTitle(), notification.getContent()))
                    .build()
            )
            .build();

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        log.info("Sent message: " + response);
    }
}
