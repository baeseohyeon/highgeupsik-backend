package com.highgeupsik.backend.api.events;

import com.highgeupsik.backend.core.model.user.User;
import lombok.Getter;

@Getter
public class CommentCreateEvent {

    private Long receiverId;

    private String content;

    public CommentCreateEvent() {
    }

    public CommentCreateEvent(User receiver, String content) {
        this.receiverId = receiver.getId();
        this.content = content;
    }
}
