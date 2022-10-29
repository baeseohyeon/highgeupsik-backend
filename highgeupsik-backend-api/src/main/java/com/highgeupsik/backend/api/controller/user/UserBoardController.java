package com.highgeupsik.backend.api.controller.user;

import com.highgeupsik.backend.api.controller.ApiResult;
import com.highgeupsik.backend.api.controller.ApiUtils;
import com.highgeupsik.backend.api.resolver.LoginUser;
import com.highgeupsik.backend.api.service.board.BoardQueryService;
import com.highgeupsik.backend.api.utils.PagingUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.highgeupsik.backend.core.dto.board.BoardResDTO;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserBoardController {

    private final BoardQueryService boardQueryService;

    @ApiOperation(value = "내가 작성한 게시글 목록 조회")
    @GetMapping("/boards/my")
    public ApiResult<Page<BoardResDTO>> myBoards(@LoginUser Long userId,
        @RequestParam(value = "page", defaultValue = PagingUtils.DEFAULT_PAGE_NUMBER) Integer pageNum) {
        return ApiUtils.success(boardQueryService.findByMyId(userId, pageNum));
    }

    @ApiOperation(value = "내가 댓글단 게시글 목로 조회")
    @GetMapping("boards/my-comments")
    public ApiResult<Page<BoardResDTO>> boardsByMyComments(@LoginUser Long userId,
        @RequestParam(value = "age", defaultValue = PagingUtils.DEFAULT_PAGE_NUMBER) Integer pageNum) {
        return ApiUtils.success(boardQueryService.findByMyComments(userId, pageNum));
    }

}
