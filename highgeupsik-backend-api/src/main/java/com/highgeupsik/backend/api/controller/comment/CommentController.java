package com.highgeupsik.backend.api.controller.comment;

import static org.springframework.http.HttpStatus.*;

import com.highgeupsik.backend.api.controller.ApiResult;
import com.highgeupsik.backend.api.controller.ApiUtils;
import com.highgeupsik.backend.api.resolver.LoginUser;
import com.highgeupsik.backend.api.service.comment.CommentService;
import com.highgeupsik.backend.core.dto.comment.CommentReqDTO;
import com.highgeupsik.backend.core.dto.comment.CommentResDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "댓글 작성")
    @PostMapping("/boards/{boardId}/comments")
    public ApiResult<CommentResDTO> saveComment(@LoginUser Long userId, @PathVariable Long boardId,
        @RequestBody CommentReqDTO dto) {
        return ApiUtils.success(commentService.saveComment(userId, boardId, dto));
    }

    @ApiOperation(value = "댓글 편집")
    @PutMapping("/comments/{commentId}")
    public ApiResult<Long> updateBoard(@LoginUser Long userId, @PathVariable Long commentId,
        @RequestBody CommentReqDTO commentReqDTO) {
        return ApiUtils.success(commentService.updateComment(userId, commentId, commentReqDTO));
    }

    @ApiOperation(value = "댓글 삭제")
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@LoginUser Long userId, @PathVariable Long commentId) {
        commentService.deleteComment(userId, commentId);
    }
}
