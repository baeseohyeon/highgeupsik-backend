package com.highgeupsik.backend.core.dto.board;

import com.highgeupsik.backend.core.model.board.Like;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LikeDTO {

    private boolean likeFlag;

    public LikeDTO(Like like) {
        likeFlag = like.getFlag();
    }
}
