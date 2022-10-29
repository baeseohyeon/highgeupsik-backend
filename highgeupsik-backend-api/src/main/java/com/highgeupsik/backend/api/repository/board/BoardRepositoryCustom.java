package com.highgeupsik.backend.api.repository.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.highgeupsik.backend.core.dto.board.BoardResDTO;
import com.highgeupsik.backend.core.dto.board.BoardSearchCondition;

public interface BoardRepositoryCustom {

    Page<BoardResDTO> findAll(BoardSearchCondition condition, Pageable pageable);
}
