package com.highgeupsik.backend.api.repository.comment;

import com.highgeupsik.backend.core.model.board.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {

    Page<Comment> findCommentsBy(Long boardId, Pageable pageable);
}
