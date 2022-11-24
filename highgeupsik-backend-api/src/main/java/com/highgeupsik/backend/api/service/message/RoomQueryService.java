package com.highgeupsik.backend.api.service.message;

import com.highgeupsik.backend.api.repository.message.RoomRepository;
import com.highgeupsik.backend.core.dto.message.RoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoomQueryService {

    private final RoomRepository roomRepository;
    private static final int ROOM_COUNT = 10;

    public Page<RoomDTO> findAllBySenderId(Long userId, Integer pageNum) {
        return roomRepository.findAllBySenderId(userId, PageRequest.of(pageNum, ROOM_COUNT))
            .map(RoomDTO::new);
    }
}
