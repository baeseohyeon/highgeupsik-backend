package com.highgeupsik.backend.core.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreateRequest {

    private Long boardId;
    private Long receiverId;
}
