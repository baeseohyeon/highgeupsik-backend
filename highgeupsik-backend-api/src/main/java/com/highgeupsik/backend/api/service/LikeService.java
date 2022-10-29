package com.highgeupsik.backend.api.service;

import com.highgeupsik.backend.api.repository.LikeRepository;
import com.highgeupsik.backend.api.repository.board.BoardRepository;
import com.highgeupsik.backend.core.exception.ErrorMessages;
import com.highgeupsik.backend.core.exception.ResourceNotFoundException;
import com.highgeupsik.backend.core.model.board.Board;
import com.highgeupsik.backend.core.model.board.Comment;
import com.highgeupsik.backend.core.model.board.Like;
import com.highgeupsik.backend.core.model.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highgeupsik.backend.api.repository.comment.CommentRepository;
import com.highgeupsik.backend.api.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public boolean saveOrUpdateBoardLike(Long userId, Long boardId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
            ErrorMessages.USER_NOT_FOUND));
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BOARD_NOT_FOUND));
        Like like = likeRepository.findByUserIdAndBoardId(userId, boardId)
            .map(Like::update).orElse(Like.of(user));
        like.setBoard(board);
        board.updateBoardLikeCount(like.getFlag());
        likeRepository.save(like);
        return like.getFlag();
    }

    public boolean saveOrUpdateCommentLike(Long userId, Long commentId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.COMMENT_NOT_FOUND));
        Like like = likeRepository.findByUserIdAndCommentId(userId, commentId)
            .map(Like::update).orElse(Like.of(user));
        like.setComment(comment);
        comment.updateCommentLike(like.getFlag());
        likeRepository.save(like);
        return like.getFlag();
    }
}
