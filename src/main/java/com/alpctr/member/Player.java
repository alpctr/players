package com.alpctr.member;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import com.alpctr.data.MessageData;
import com.alpctr.data.StartingData;
import com.alpctr.data.StoppingData;
import com.alpctr.event.Executor;
import com.alpctr.players.DataBus;
import com.alpctr.players.DataType;
import com.alpctr.players.Member;

/**
 * Player class sends data to Data-Bus and receives data from Data-Bus. Accepts
 * coming data from Data-Bus with accept method and handle events according to
 * instance type of data. If MessageData data reaches Player replies to sender
 * with a MessageData.
 */
public class Player implements Member {

	private static final int INITIAL_COUNTER_VALUE = 0;
	private static final int MAX_SEND_COUNT = 10;

	private String name;
	private CountDownLatch latch;
	private AtomicInteger sendCounter = new AtomicInteger(INITIAL_COUNTER_VALUE);

	public Player(String name) {
		super();
		this.name = name;
	}

	/**
	 * handle events according to instance type of data
	 *
	 * @param data The data comes from data-bus
	 */
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

	/**
	 * When a player receives a message with the data type of StartingData, prints it to the standart output.
	 *
	 * @param data The data comes from data-bus
	 */
	private void handleEvent(StartingData data) {
		System.out.println(String.format("Receiver %s sees message:\"%s\" at %s", name, data.getGreeting(), data.getWhen()));
	}

	/**
	 * When a player receives a message with the data type of StoppingData, prints it to the standart output.
	 *
	 * @param data The data comes from data-bus
	 */
	private void handleEvent(StoppingData data) {
		System.out.println(String.format("Receiver %s sees message:%s at %s", name, data.getGoodbye(), data.getWhen()));
	}

	/**
	 * When a player receives a message, replies with a message that contains the
	 * received message concatenated with the value of a counter holding the number
	 * of messages this player already sent.
	 *
	 * @param data The data comes from data-bus
	 */

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
		String concatenatedMessage = sb.toString();

		System.out.println(String.format("Received message: %s in %s with thread %d", message, name,
				Thread.currentThread().getId()));

		if (latch.getCount() == 0) {
			data.getDataBus().unsubscribe(this);
			return;
		}

		send(MessageData.of(concatenatedMessage), data.getDataBus());
		System.out.println(String.format("Sent message: %s in %s", concatenatedMessage, name));

		latch.countDown();

		if (sendCount > MAX_SEND_COUNT) {
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
