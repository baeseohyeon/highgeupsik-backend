package com.highgeupsik.backend.api.controller.comment;

import com.highgeupsik.backend.api.controller.ApiResult;
import com.highgeupsik.backend.api.controller.ApiUtils;
import com.highgeupsik.backend.api.resolver.LoginUser;
import com.highgeupsik.backend.api.service.LikeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentLikeController {

    private final LikeService likeService;

    @ApiOperation(value = "댓글 좋아요")
    @PostMapping("/comments/{commentId}/likes")
    public ApiResult<Boolean> saveCommentLike(@LoginUser Long userId, @PathVariable Long commentId) {
        return ApiUtils.success(likeService.saveOrUpdateCommentLike(userId, commentId));
    }
}
