package com.highgeupsik.backend.core.model.message;

import com.highgeupsik.backend.core.model.TimeEntity;
import com.highgeupsik.backend.core.model.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Message extends TimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_id")
	private Long id;

	private String content;

	private boolean isReceiverRead;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	private User sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id")
	private User receiver;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private User owner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;

	@Builder
	public Message(User sender, User receiver, User owner, String content, boolean isReceiverRead) {
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
		this.owner = owner;
		this.isReceiverRead = isReceiverRead;
	}

	public static Message send(User sender, User receiver, String content) {
		return Message.builder()
			.sender(sender)
			.receiver(receiver)
			.owner(sender)
			.content(content)
			.build();
	}

	public static Message receive(User sender, User receiver, String content) {
		return Message.builder()
			.sender(sender)
			.receiver(receiver)
			.owner(sender)
			.content(content)
			.isReceiverRead(true)
			.build();
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}
