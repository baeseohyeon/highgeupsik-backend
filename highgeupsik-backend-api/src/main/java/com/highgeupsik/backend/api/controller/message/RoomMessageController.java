package com.highgeupsik.backend.api.controller.message;

import static com.highgeupsik.backend.api.controller.ApiUtils.success;
import static org.springframework.http.HttpStatus.CREATED;

import com.highgeupsik.backend.api.controller.ApiResult;
import com.highgeupsik.backend.api.resolver.LoginUser;
import com.highgeupsik.backend.api.service.message.MessageQueryService;
import com.highgeupsik.backend.api.service.message.RoomMessageService;
import com.highgeupsik.backend.api.service.message.RoomQueryService;
import com.highgeupsik.backend.api.utils.PagingUtils;
import com.highgeupsik.backend.core.dto.message.MessageReqDTO;
import com.highgeupsik.backend.core.dto.message.MessageResDTO;
import com.highgeupsik.backend.core.dto.message.OnlyIdDTO;
import com.highgeupsik.backend.core.dto.message.RoomCreateRequest;
import com.highgeupsik.backend.core.dto.message.RoomDTO;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/rooms")
@RestController
public class RoomMessageController {

	private final RoomMessageService roomMessageService;
	private final RoomQueryService roomQueryService;
	private final MessageQueryService messageQueryService;

	@ApiOperation(value = "메세지룸 목록 조회")
	@GetMapping
	public ApiResult<Page<RoomDTO>> rooms(@LoginUser Long userId,
		@RequestParam(value = "page", defaultValue = PagingUtils.DEFAULT_PAGE_NUMBER) Integer pageNum) {
		return success(roomQueryService.findAllBySenderId(userId, pageNum));
	}

	@ApiOperation(value = "메세지 목록 조회")
	@GetMapping("/{roomId}")
	public ApiResult<List<MessageResDTO>> messages(@PathVariable Long roomId,
		@RequestParam(value = "lastMessageId", required = false) Long lastMessageId) {
		roomMessageService.readNewMessagesByRoomId(roomId);
		return success(messageQueryService.findAllByRoomIdAndOwnerId(roomId, lastMessageId));
	}

	@ApiOperation(value = "메시지 룸 생성")
	@PostMapping
	public ApiResult<OnlyIdDTO> createRoom(@LoginUser Long userId, @RequestBody RoomCreateRequest req) {
		return success(roomMessageService.createRoom(userId, req.getReceiverId(), req.getBoardId()));
	}

	@ApiOperation(value = "메세지룸 삭제")
	@DeleteMapping("/{roomId}")
	public void deleteRoom(@LoginUser Long userId, @PathVariable Long roomId) {
		roomMessageService.deleteRoom(userId, roomId);
	}

	@ApiOperation(value = "메세지 전송")
	@ResponseStatus(CREATED)
	@PostMapping("/{roomId}/messages")
	public ApiResult<Long> sendMessage(@PathVariable Long roomId, @RequestBody MessageReqDTO messageReqDTO) {
		return success(roomMessageService.sendMessage(roomId, messageReqDTO.getContent()));
	}
}
