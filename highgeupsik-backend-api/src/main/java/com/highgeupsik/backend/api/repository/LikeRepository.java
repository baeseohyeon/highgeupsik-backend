package com.highgeupsik.backend.api.repository;

import com.highgeupsik.backend.core.model.board.Comment;
import com.highgeupsik.backend.core.model.board.Like;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndBoardId(Long userId, Long boardId);

    Optional<Like> findByUserIdAndCommentId(Long userId, Long commentId);

    List<Like> findAllByUserIdAndCommentIn(Long userId, List<Comment> comments);
}
