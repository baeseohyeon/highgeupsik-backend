package com.highgeupsik.backend.api.controller.notification;

import com.highgeupsik.backend.api.resolver.LoginUser;
import com.highgeupsik.backend.api.service.notification.SubscriptionService;
import com.highgeupsik.backend.core.dto.notification.SubscriptionReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscriptions")
    public void saveSubscription(@LoginUser Long userId, @RequestBody SubscriptionReq subscriptionReq) {
        System.out.println(subscriptionReq.getFcmToken());
        subscriptionService.saveSubscription(userId, subscriptionReq.getFcmToken());
    }
}
