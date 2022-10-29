package com.highgeupsik.backend.api.repository.board;

import static org.springframework.util.StringUtils.hasText;

import com.highgeupsik.backend.core.dto.board.BoardResDTO;
import com.highgeupsik.backend.core.dto.board.BoardSearchCondition;
import com.highgeupsik.backend.core.dto.board.QBoardResDTO;
import com.highgeupsik.backend.core.model.board.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Page<BoardResDTO> findAll(BoardSearchCondition condition, Pageable pageable) {

        BooleanBuilder builder = getBuilder(condition);

        List<BoardResDTO> content = query
            .select(new QBoardResDTO(QBoard.board.id, QBoard.board.user.id, QBoard.board.title, QBoard.board.content,
                QBoard.board.thumbnail, QBoard.board.category, QBoard.board.likeCount, QBoard.board.commentCount, QBoard.board.createdDate))
            .from(QBoard.board)
            .where(builder)
            .orderBy(QBoard.board.createdDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> total = query
            .select(QBoard.board.count())
            .from(QBoard.board)
            .where(builder);

        return PageableExecutionUtils.getPage(content, pageable, total::fetchOne);
    }

    private BooleanBuilder getBuilder(BoardSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QBoard.board.deleteFlag.eq(false));
        if (hasText(condition.getKeyword())) {
            builder.and(QBoard.board.title.contains(condition.getKeyword())
                .or(QBoard.board.content.contains(condition.getKeyword())));
        }
        if (hasText(condition.getRegion().toString())) {
            builder.and(QBoard.board.region.eq(condition.getRegion()));
        }
        if (condition.getCategory() != null && hasText(condition.getCategory().toString())) {
            builder.and(QBoard.board.category.eq(condition.getCategory()));
        }
        if (condition.getLikeCount() != null) {
            builder.and(QBoard.board.likeCount.goe(condition.getLikeCount()));
        }
        return builder;
    }
}
