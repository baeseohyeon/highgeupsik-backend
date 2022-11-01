package com.highgeupsik.backend.api.service.notification;

import static com.highgeupsik.backend.core.exception.ErrorMessages.*;

import com.highgeupsik.backend.core.events.CommentCreateEvent;
import com.highgeupsik.backend.api.repository.notification.NotificationRepository;
import com.highgeupsik.backend.core.exception.ResourceNotFoundException;
import com.highgeupsik.backend.core.model.board.Board;
import com.highgeupsik.backend.core.model.board.Comment;
import com.highgeupsik.backend.core.model.message.Room;
import com.highgeupsik.backend.core.model.notification.Notification;
import com.highgeupsik.backend.core.model.notification.NotificationType;
import com.highgeupsik.backend.core.model.user.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class NotificationService {

	private final NotificationRepository notificationRepository;
	private final ApplicationEventPublisher applicationEventPublisher;

	@Transactional(propagation = Propagation.MANDATORY)
	public void saveCommentNotification(User user, Board board, Comment comment, NotificationType type) {
		Notification notification = Notification.of(user, comment.getContent(), type, board, comment);
		notificationRepository.save(notification);
		applicationEventPublisher.publishEvent(new CommentCreateEvent(user, comment.getContent()));
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public void saveRoomNotification(User user, Room room) {
		Notification notification = Notification.ofRoom(user, room.getRecentMessage(), NotificationType.MESSAGE, room);
		notificationRepository.save(notification);
	}

	public void readNotification(Long notificationId) {
		Notification notification = notificationRepository.findById(notificationId)
			.orElseThrow(() -> new ResourceNotFoundException(NOTIFICATION_NOT_FOUND));
		notification.readNotification();
	}

	public void deleteByComment(Comment comment) {
		notificationRepository.deleteByComment(comment);
		notificationRepository.deleteAllByCommentIn(comment.getChildren());
	}

	public void deleteByRoom(Room room) {
		notificationRepository.deleteAllByRoom(room);
	}
}
