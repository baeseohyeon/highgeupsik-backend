package com.highgeupsik.backend.api.repository.message;

import static com.highgeupsik.backend.core.model.message.QRoom.room;

import com.highgeupsik.backend.core.model.message.QMessage;
import com.highgeupsik.backend.core.model.message.Room;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Page<Room> findAllBySenderId(Long userId, Pageable pageable) {
        List<Room> rooms = query.selectFrom(room)
            .join(room, QMessage.message.room)
            .where(roomSenderIdEq(userId))
            .groupBy(room.id)
            .orderBy(QMessage.message.createdDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> roomCount = query
            .select(room.count())
            .where(roomSenderIdEq(userId));

        return PageableExecutionUtils.getPage(rooms, pageable, roomCount::fetchOne);
    }

    private BooleanExpression roomSenderIdEq(Long userId) {
        return room.sender.id.eq(userId);
    }
}
