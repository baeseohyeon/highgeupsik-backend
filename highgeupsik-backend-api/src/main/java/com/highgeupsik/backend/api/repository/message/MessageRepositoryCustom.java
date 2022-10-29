package com.highgeupsik.backend.api.repository.message;

import com.highgeupsik.backend.core.model.message.Message;
import java.util.List;

public interface MessageRepositoryCustom {

    List<Message> findAllByRoomIdAndIdLessThan(Long roomId, Long lastMessageId, int MESSAGE_COUNT);
}
