package com.highgeupsik.backend.api.service.message;

import com.highgeupsik.backend.api.repository.message.MessageRepository;
import com.highgeupsik.backend.core.dto.message.MessageResDTO;
import com.highgeupsik.backend.api.utils.PagingUtils;
import com.highgeupsik.backend.core.model.message.Message;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MessageQueryService {

    private final MessageRepository messageRepository;

    public List<MessageResDTO> findAllByRoomIdAndOwnerId(Long roomId, Long lastMessageId) {
        return messageRepository.findAllByRoomIdAndIdLessThan(roomId, lastMessageId, PagingUtils.MESSAGE_COUNT)
            .stream()
            .sorted(Comparator.comparing(Message::getId))
            .map((MessageResDTO::new)).collect(Collectors.toList());
    }
}
