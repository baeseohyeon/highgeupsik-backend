package com.highgeupsik.backend.core.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class JoinEvent {

    private String email;
    private String username;
    private boolean joined;

    public static JoinEvent ofSuccess(String email, String username) {
        JoinEvent event = new JoinEvent();
        event.email = email;
        event.username = username;
        event.joined = true;
        return event;
    }

    public static JoinEvent ofFail(String email, String username) {
        JoinEvent event = new JoinEvent();
        event.email = email;
        event.username = username;
        return event;
    }
}
