package com.highgeupsik.backend.api.repository;

import com.highgeupsik.backend.core.model.board.Comment;
import com.highgeupsik.backend.core.model.message.Room;
import com.highgeupsik.backend.core.model.notification.Notification;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findAllByReceiverId(Long receiverId, Pageable pageable);

    void deleteByComment(Comment comment);

    void deleteAllByCommentIn(List<Comment> comments);

    void deleteAllByRoom(Room room);
}
