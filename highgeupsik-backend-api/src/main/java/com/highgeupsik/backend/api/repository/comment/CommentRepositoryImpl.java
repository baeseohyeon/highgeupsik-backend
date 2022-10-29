package com.highgeupsik.backend.api.repository.comment;

import com.highgeupsik.backend.core.model.board.Comment;
import com.highgeupsik.backend.core.model.board.QComment;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Page<Comment> findCommentsBy(Long boardId, Pageable pageable) {
        List<Comment> comments = query.selectFrom(QComment.comment)
            .where(QComment.comment.board.id.eq(boardId))
            .orderBy(QComment.comment.parent.id.asc(), QComment.comment.id.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> commentCount = query
            .select(QComment.comment.count())
            .where(QComment.comment.board.id.eq(boardId));

        return PageableExecutionUtils.getPage(comments, pageable, commentCount::fetchOne);
    }
}
