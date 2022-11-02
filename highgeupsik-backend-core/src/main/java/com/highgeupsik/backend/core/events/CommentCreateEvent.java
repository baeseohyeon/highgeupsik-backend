package com.highgeupsik.backend.core.events;

import com.highgeupsik.backend.core.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentCreateEvent {

    private Long receiverId;

    private String content;

    public CommentCreateEvent(User receiver, String content) {
        this.receiverId = receiver.getId();
        this.content = content;
    }
}
