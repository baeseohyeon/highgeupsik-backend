package com.highgeupsik.backend.api.service.comment;

import com.highgeupsik.backend.core.dto.comment.CommentReqDTO;
import com.highgeupsik.backend.core.dto.comment.CommentResDTO;
import com.highgeupsik.backend.api.repository.board.BoardRepository;
import com.highgeupsik.backend.api.repository.comment.CommentRepository;
import com.highgeupsik.backend.api.repository.user.UserRepository;
import com.highgeupsik.backend.api.service.notification.NotificationService;
import com.highgeupsik.backend.core.exception.ErrorMessages;
import com.highgeupsik.backend.core.exception.ResourceNotFoundException;
import com.highgeupsik.backend.core.model.board.Board;
import com.highgeupsik.backend.core.model.board.Comment;
import com.highgeupsik.backend.core.model.notification.NotificationType;
import com.highgeupsik.backend.core.model.user.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private static final int BOARD_WRITER_ANONYMOUS_NUMBER = -1;

    public CommentResDTO saveComment(Long userId, Long boardId, CommentReqDTO commentDto) {
        User commentWriter = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BOARD_NOT_FOUND));

        Comment comment = Comment.of(commentDto.getContent(), commentWriter, board,
            getAnonymousNumberFrom(board, commentWriter));
        comment.setBoard(board);
        commentRepository.save(comment);

        Set<Long> receiverIds = new HashSet<>(); //알림 보내기
        if (isReply(commentDto)) { //작성한 댓글이 대댓글이면
            Comment parent = commentRepository.findById(commentDto.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.COMMENT_NOT_FOUND));
            initReceiverIds(comment, parent, receiverIds);
            sendNotificationToReplyWriter(board, comment, receiverIds);
            transformToReply(comment, parent);
        }
        sendNotificationToBoardWriter(board, comment, receiverIds);

        return new CommentResDTO(comment);
    }

    private boolean isReply(CommentReqDTO dto) {
        return dto.getParentId() != null;
    }

    private int getAnonymousNumberFrom(Board board, User commentWriter) {
        if (board.isWriter(commentWriter)) {
            return BOARD_WRITER_ANONYMOUS_NUMBER;
        }
        return commentRepository.findFirstByBoardAndUser(board, commentWriter)
            .map(Comment::getAnonymousId)
            .orElseGet(board::getNextAnonymousNumber);
    }

    private void transformToReply(Comment comment, Comment parent) {
        comment.toReply(parent);
    }

    private void sendNotificationToReplyWriter(Board board, Comment comment, Set<Long> receiverIds) {
        receiverIds.stream()
            .map(id -> userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                ErrorMessages.USER_NOT_FOUND)))
            .forEach((u) -> notificationService.saveCommentNotification(u, board, comment, NotificationType.REPLY));
    }

    private void sendNotificationToBoardWriter(Board board, Comment comment, Set<Long> receiverIds) {
        if (isBoardWriterHasNotReceiveNotification(board, comment, receiverIds)) {
            notificationService.saveCommentNotification(board.getUser(), board, comment, NotificationType.COMMENT);
        }
    }

    private boolean isBoardWriterHasNotReceiveNotification(Board board, Comment comment, Set<Long> receiverIds) {
        return !board.isWriter(comment.getUser()) && !receiverIds.contains(board.getUser().getId());
    }

    public Long updateComment(Long userId, Long commentId, CommentReqDTO commentReqDTO) {
        User writer = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.COMMENT_NOT_FOUND));
        comment.validateWriter(writer);
        comment.updateContent(commentReqDTO);
        return commentId;
    }

    public void deleteComment(Long userId, Long commentId) {
        User writer = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.COMMENT_NOT_FOUND));
        Comment parent = comment.getParent();
        comment.validateWriter(writer);
        comment.disable();

        parent.deleteReply();

        if (parent.isDisabled() && parent.canDelete()) {
            deleteNotifications(parent);
            parent.deleteComment();
        }

        Board board = comment.getBoard();
        board.deleteCommentCount();
    }

    public void deleteNotifications(Comment comment) {
        notificationService.deleteByComment(comment);
    }

    private void initReceiverIds(Comment newComment, Comment parent, Set<Long> receiverIds) {
        List<Comment> children = new ArrayList<>(parent.getChildren());
        children.add(parent);
        receiverIds.addAll(children.stream()
            .filter(c -> c.isNotWriter(newComment.getUser()))
            .map(c -> c.getUser().getId())
            .collect(Collectors.toList()));
    }
}