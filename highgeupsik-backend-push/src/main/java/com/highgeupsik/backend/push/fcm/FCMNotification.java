package com.highgeupsik.backend.push.fcm;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FCMNotification {

    private String title;
    private String content;
    private String token;

    public static FCMNotification ofNewComment(String content, String token) {
        FCMNotification notification = new FCMNotification();
        notification.title = "새로운 댓글이 작성되었습니다.";
        notification.content = content;
        notification.token = token;
        return notification;
    }
}
