package com.alpctr.member;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import com.alpctr.data.MessageData;
import com.alpctr.data.StartingData;
import com.alpctr.data.StoppingData;
import com.alpctr.players.DataBus;
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
		if (data instanceof MessageData) {
			Executor executor = Executor.getInstance();

			executor.getExecutor().execute(() -> {
				handleEvent((MessageData) data);
			});
		} else if (data instanceof StartingData) {
			handleEvent((StartingData) data);
		} else if (data instanceof StoppingData) {
			handleEvent((StoppingData) data);
		}

	}
	
	public void send(final DataType event, DataBus bus) {
		bus.publish(event, this);		
	}

	private void handleEvent(StartingData data) {
	    System.out.println(String.format("Receiver %s sees message:\"%s\" at %s", name, data.getGreeting(), data.getWhen()));
	}
	
	private void handleEvent(StoppingData data) {
		System.out.println(String.format("Receiver %s sees message:%s at %s", name, data.getGoodbye(), data.getWhen()));
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
		
		send(MessageData.of(newMessage), data.getDataBus());
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
