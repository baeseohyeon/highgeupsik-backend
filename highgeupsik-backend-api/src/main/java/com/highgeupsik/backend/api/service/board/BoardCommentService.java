package com.highgeupsik.backend.api.service.board;

import static java.util.stream.Collectors.*;

import com.highgeupsik.backend.api.repository.LikeRepository;
import com.highgeupsik.backend.api.repository.comment.CommentRepository;
import com.highgeupsik.backend.core.model.board.Comment;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highgeupsik.backend.core.dto.comment.CommentResDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardCommentService {

    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Transactional(readOnly = true)
    public Page<CommentResDTO> findCommentsBy(Long userId, Long boardId, Integer pageNum, Integer pageSize) {
        Page<Comment> comments = commentRepository.findCommentsBy(boardId, PageRequest.of(pageNum, pageSize));
        Set<Long> likes = getUserLikeSet(userId, comments);
        return comments.map(comment -> new CommentResDTO(comment, likes.contains(comment.getId())));
    }

    private Set<Long> getUserLikeSet(Long userId, Page<Comment> comments) {
        return likeRepository.findAllByUserIdAndCommentIn(userId, comments.getContent())
            .stream()
            .map(like -> like.getComment().getId())
            .collect(toSet());
    }
}
