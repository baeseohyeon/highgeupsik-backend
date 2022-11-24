package com.highgeupsik.backend.api.repository.message;

import com.highgeupsik.backend.core.model.message.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomRepositoryCustom {

    Page<Room> findAllBySenderId(Long userId, Pageable pageable);
}
