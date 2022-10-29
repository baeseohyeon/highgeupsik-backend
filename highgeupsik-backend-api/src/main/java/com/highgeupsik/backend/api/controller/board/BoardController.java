package com.highgeupsik.backend.api.controller.board;

import static org.springframework.http.HttpStatus.*;

import com.highgeupsik.backend.api.controller.ApiResult;
import com.highgeupsik.backend.api.controller.ApiUtils;
import com.highgeupsik.backend.api.resolver.LoginUser;
import com.highgeupsik.backend.api.service.board.BoardQueryService;
import com.highgeupsik.backend.api.service.board.BoardService;
import com.highgeupsik.backend.api.utils.PagingUtils;
import com.highgeupsik.backend.core.dto.board.BoardReqDTO;
import com.highgeupsik.backend.core.dto.board.BoardResDTO;
import com.highgeupsik.backend.core.dto.board.BoardSearchCondition;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/boards")
@RestController
public class BoardController {

    private final BoardService boardService;
    private final BoardQueryService boardQueryService;

    @ApiOperation(value = "게시글 단일 조회")
    @GetMapping("/{boardId}")
    public ApiResult<BoardResDTO> boardDetails(@LoginUser Long userId, @PathVariable Long boardId) {
        return ApiUtils.success(boardQueryService.findOneById(userId, boardId));
    }

    @ApiOperation(value = "게시글 목록 조회")
    @GetMapping
    public ApiResult<Page<BoardResDTO>> boards(@RequestParam(value = "page", defaultValue = PagingUtils.DEFAULT_PAGE_NUMBER) Integer pageNum,
        @Valid BoardSearchCondition condition) {
        return ApiUtils.success(boardQueryService.findAll(pageNum, condition));
    }

    @ApiOperation(value = "게시글 작성")
    @PostMapping
    public ApiResult<Long> saveBoard(@LoginUser Long userId, @RequestBody BoardReqDTO boardReqDTO) {
        return ApiUtils.success(boardService.makeBoard(userId, boardReqDTO));
    }

    @ApiOperation(value = "게시글 편집")
    @PutMapping("/{boardId}")
    public ApiResult<Long> updateBoard(@LoginUser Long userId, @PathVariable Long boardId,
        @RequestBody BoardReqDTO boardReqDTO) {
        return ApiUtils.success(boardService.updateBoard(userId, boardId, boardReqDTO));
    }

    @ApiOperation(value = "게시글 삭제")
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{boardId}")
    public void deleteBoard(@LoginUser Long userId, @PathVariable Long boardId) {
        boardService.deleteBoard(userId, boardId);
    }
}
