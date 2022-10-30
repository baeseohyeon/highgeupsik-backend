package com.highgeupsik.backend.core.dto.notification;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SubscriptionReq {

    private String fcmToken;
}
