package com.alpctr.member;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.alpctr.data.MessageData;
import com.alpctr.players.DataBus;
import com.alpctr.players.DataType;
import com.alpctr.players.Member;
import com.alpctr.players.Subscribable;

public class Player implements Member {

	private String name;
	private String message;
	private LocalDateTime started;
	private LocalDateTime stopped;

	private final AtomicReference<DataBus> bus = new AtomicReference<>();
	private final AtomicInteger sendCounter = new AtomicInteger();
	//private final AtomicInteger receiveCounter = new AtomicInteger();

	public Player(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public void accept(DataType data) {
		handleEvent((MessageData) data);
	}

	private void handleEvent(MessageData data) {
		String message;
		int sendCount;
		message = data.getMessage();
		
		sendCount = sendCounter.incrementAndGet();
		
		String newMessage = message + "|" + sendCount;
		
		System.out.println(String.format("Received message: %s in %s", message, name));
		data.getDataBus().publish(MessageData.of(newMessage), this);
		System.out.println(String.format("Sent message: %s in %s", newMessage, name));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
