package com.highgeupsik.backend.api.repository.message;

import com.highgeupsik.backend.core.model.board.Board;
import com.highgeupsik.backend.core.model.message.Room;
import com.highgeupsik.backend.core.model.user.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByBoardAndSender(Board board, User sender);

    Optional<Room> findByBoardAndSenderAndReceiver(Board board, User sender, User receiver);

    Page<Room> findAllBySenderId(Long senderId, Pageable pageable);
}
