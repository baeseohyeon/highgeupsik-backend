package com.highgeupsik.backend.core.model.message;

import com.highgeupsik.backend.core.exception.ErrorMessages;
import com.highgeupsik.backend.core.exception.UserException;
import com.highgeupsik.backend.core.model.TimeEntity;
import com.highgeupsik.backend.core.model.board.Board;
import com.highgeupsik.backend.core.model.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Room extends TimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private Long id;

	private String recentMessage;

	private int newMessageCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	private User sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id")
	private User receiver;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
	private final List<Message> messageList = new ArrayList<>();

	@Builder
	public Room(User sender, User receiver, Board board) {
		this.sender = sender;
		this.receiver = receiver;
		this.board = board;
	}

	public static Room of() { //테스트를 위한 스태틱 메소드
		return new Room();
	}

	public static Room ofBoard(User sender, User receiver, Board board) {
		return Room.builder()
			.sender(sender)
			.receiver(receiver)
			.board(board)
			.build();
	}

	public void addMessage(Message message) {
		messageList.add(message);
		message.setRoom(this);
		setRecentMessage(message.getContent());
	}

	public void sendMessage(String content) {
		addMessage(Message.send(sender, receiver, content));
	}

	public void receiveMessage(String content) {
		addMessage(Message.receive(receiver, sender, content));
		addNewMessageCount();
	}

	public void addNewMessageCount() {
		newMessageCount++;
	}

	public void readNewMessages() {
		newMessageCount = 0;
	}

	public void setRecentMessage(String recentMessage) {
		this.recentMessage = recentMessage;
	}

	public void checkUser(Long userId) {
		if (isNotRoomUser(userId)) {
			throw new UserException(ErrorMessages.NOT_ROOM_USER);
		}
	}

	private boolean isNotRoomUser(Long userId) {
		return !sender.getId().equals(userId);
	}
}
