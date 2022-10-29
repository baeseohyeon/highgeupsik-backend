package com.highgeupsik.backend.core.dto.message;

import com.highgeupsik.backend.core.model.message.Room;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class RoomDTO {

    private Long roomId;
    private Long boardId;
    private Long receiverId;
    private String recentMessage;
    private int newMessageCount;
    private LocalDateTime modifiedDate;

    public RoomDTO(Room room) {
        roomId = room.getId();
        boardId = room.getBoard().getId();
        receiverId = room.getReceiver().getId();
        newMessageCount = room.getNewMessageCount();
        recentMessage = room.getRecentMessage();
        modifiedDate = room.getModifiedDate();
    }
}
