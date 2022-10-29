package com.highgeupsik.backend.api.service.message;

import static com.highgeupsik.backend.api.utils.PagingUtils.*;

import com.highgeupsik.backend.api.repository.message.RoomRepository;
import com.highgeupsik.backend.core.dto.message.RoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoomQueryService {

    private final RoomRepository roomRepository;
    private static final int ROOM_COUNT = 10;

    public Page<RoomDTO> findAllByMyId(Long userId, Integer pageNum) {
        return roomRepository.findAllBySenderId(userId, orderByModifiedDate(pageNum, ROOM_COUNT))
            .map(RoomDTO::new);
    }
}
