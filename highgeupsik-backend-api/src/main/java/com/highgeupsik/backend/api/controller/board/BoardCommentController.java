package com.highgeupsik.backend.api.controller.board;

import com.highgeupsik.backend.api.controller.ApiResult;
import com.highgeupsik.backend.api.controller.ApiUtils;
import com.highgeupsik.backend.core.dto.comment.CommentResDTO;
import com.highgeupsik.backend.api.resolver.LoginUser;
import com.highgeupsik.backend.api.service.board.BoardCommentService;
import com.highgeupsik.backend.api.utils.PagingUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BoardCommentController {

    private final BoardCommentService boardCommentService;

    @ApiOperation(value = "게시글의 댓글 목록 조회")
    @GetMapping("/boards/{boardId}/comments")
    public ApiResult<Page<CommentResDTO>> boardComments(
        @LoginUser Long userId,
        @PathVariable Long boardId,
        @RequestParam(value = "page", defaultValue = PagingUtils.DEFAULT_PAGE_NUMBER) int pageNum,
        @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        return ApiUtils.success(boardCommentService.findCommentsBy(userId, boardId, pageNum, pageSize));
    }
}
