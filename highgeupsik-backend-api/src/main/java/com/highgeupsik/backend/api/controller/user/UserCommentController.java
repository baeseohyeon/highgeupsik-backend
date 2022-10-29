package com.highgeupsik.backend.api.controller.user;

import com.highgeupsik.backend.api.controller.ApiResult;
import com.highgeupsik.backend.api.controller.ApiUtils;
import com.highgeupsik.backend.core.dto.comment.CommentResDTO;
import com.highgeupsik.backend.api.resolver.LoginUser;
import com.highgeupsik.backend.api.service.comment.CommentQueryService;
import com.highgeupsik.backend.api.utils.PagingUtils;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserCommentController {

    private final CommentQueryService commentQueryService;

    @ApiOperation(value = "내가 작성한 댓글 목록 조회")
    @GetMapping("/comments/my")
    public ApiResult<Page<CommentResDTO>> myComments(@LoginUser Long userId,
        @RequestParam(value = "page", defaultValue = PagingUtils.DEFAULT_PAGE_NUMBER) Integer pageNum) {
        return ApiUtils.success(commentQueryService.findByMyId(userId, pageNum));
    }
}
