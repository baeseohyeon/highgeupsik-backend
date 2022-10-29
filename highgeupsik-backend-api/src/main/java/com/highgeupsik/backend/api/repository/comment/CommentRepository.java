package com.highgeupsik.backend.api.repository.comment;

import com.highgeupsik.backend.core.model.board.Board;
import com.highgeupsik.backend.core.model.board.Comment;
import com.highgeupsik.backend.core.model.user.User;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    @EntityGraph(attributePaths = {"board", "parent"})
    Optional<Comment> findById(Long commentId);

    Optional<Comment> findFirstByBoardAndUser(Board board, User user);

    Page<Comment> findByUserId(Long userId, Pageable pageable);
}
