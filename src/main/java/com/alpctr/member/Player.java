package com.alpctr.member;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import com.alpctr.data.MessageData;
import com.alpctr.players.DataType;
import com.alpctr.players.Member;

public class Player implements Member {

	private String name;
	private CountDownLatch latch;

	private AtomicInteger sendCounter = new AtomicInteger(0);

	public Player(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public void accept(DataType data) {
		Executor executor = Executor.getInstance();

		executor.getExecutor().execute(() -> {
			handleEvent((MessageData) data);
			});
		
	}

	private void handleEvent(MessageData data) {
		String message;
		String delimiter = "|";
		int sendCount = 0;
		message = data.getMessage();
		StringBuilder sb = new StringBuilder();
		
		sendCount = sendCounter.getAndIncrement();
		sb.append(message);
		sb.append(delimiter);
		sb.append(sendCount);
		String newMessage = sb.toString();
		
		System.out.println(String.format("Received message: %s in %s with thread %d", message, name, Thread.currentThread().getId()));
		
		if (latch.getCount() == 0) {
			return;
		}
		
		data.getDataBus().publish(MessageData.of(newMessage), this);
		System.out.println(String.format("Sent message: %s in %s", newMessage, name));
		
		
		latch.countDown();
		
		if (sendCount > 10) {
			data.getDataBus().unsubscribe(this);
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	public AtomicInteger getSendCounter() {
		return sendCounter;
	}

	public void setSendCounter(AtomicInteger sendCounter) {
		this.sendCounter = sendCounter;
	}


}
