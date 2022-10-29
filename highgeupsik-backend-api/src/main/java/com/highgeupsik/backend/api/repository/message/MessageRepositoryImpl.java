package com.highgeupsik.backend.api.repository.message;

import com.highgeupsik.backend.core.model.message.Message;
import com.highgeupsik.backend.core.model.message.QMessage;
import com.highgeupsik.backend.core.model.message.QRoom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Message> findAllByRoomIdAndIdLessThan(Long roomId, Long lastMessageId, int MESSAGE_COUNT) {
        return query.selectFrom(QMessage.message)
            .join(QMessage.message.room, QRoom.room)
            .where(roomIdEq(roomId), lastMessageIdLt(lastMessageId))
            .orderBy(QMessage.message.id.desc())
            .limit(MESSAGE_COUNT)
            .fetch();
    }

    private BooleanExpression roomIdEq(Long roomId) {
        return QMessage.message.room.id.eq(roomId);
    }

    private BooleanExpression lastMessageIdLt(Long lastMessageId) {
        return Objects.nonNull(lastMessageId) ? QMessage.message.id.lt(lastMessageId) : null;
    }
}
