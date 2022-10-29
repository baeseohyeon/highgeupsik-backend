package com.highgeupsik.backend.api.controller.board;

import com.highgeupsik.backend.api.controller.ApiResult;
import com.highgeupsik.backend.api.controller.ApiUtils;
import com.highgeupsik.backend.api.resolver.LoginUser;
import com.highgeupsik.backend.api.service.LikeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/boards")
@RestController
public class BoardLikeController {

    private final LikeService likeService;

    @ApiOperation(value = "게시글 좋아요")
    @PostMapping("/{boardId}/likes")
    public ApiResult<Boolean> saveBoardLike(@LoginUser Long userId, @PathVariable Long boardId) {
        return ApiUtils.success(likeService.saveOrUpdateBoardLike(userId, boardId));
    }
}
