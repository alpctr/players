package com.alpctr.players;

import static java.lang.System.exit;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import com.alpctr.data.MessageData;
import com.alpctr.data.StartingData;
import com.alpctr.event.Executor;
import com.alpctr.member.Player;

/*
 * Players application uses Data-Bus pattern to pass messages between each
 * other. The Data-Bus pattern provides a method where different parts of an
 * application may pass messages between each other without needing to be aware
 * of the other's existence. Similar to the ObserverPattern, players register
 * themselves with the DataBus and may then receive each piece of data that is
 * published to the Data-Bus. The player may react to any given message or not.
 * Application uses CountDownLatch to release the waiting threads and finalize
 * the program after the initiator sent 10 messages and received
 * back 10 messages
 */
class App {

	private static final int NUMBER_OF_PLAYERS = 2;
	private static final int NUMBER_OF_EVENT_LIMIT = 10;
	private static final int INITIATOR_EVENT = 1;
	private static final int INITIAL_COUNT_DOWN_LATCH_VALUE = (NUMBER_OF_EVENT_LIMIT - INITIATOR_EVENT)
			* NUMBER_OF_PLAYERS;
	private static final String MESSAGE = "Knock knock!";

	public static void main(String[] args) {

		CountDownLatch latch = new CountDownLatch(INITIAL_COUNT_DOWN_LATCH_VALUE);
		Player player1 = new Player("player1");
		Player player2 = new Player("player2");
		Player initiator = player1;
		final DataBus bus = DataBus.getInstance();

		player1.setLatch(latch);
		player2.setLatch(latch);

		bus.subscribe(player1);
		bus.subscribe(player2);

		player1.send(StartingData.of(LocalDateTime.now(), "Hey! Let's play"), bus);
		System.out.println("\n");
		initiator.getSendCounter().incrementAndGet();
		initiator.send(MessageData.of(MESSAGE), bus);
		System.out.println(String.format("Sent message: %s in %s", MESSAGE, initiator.getName()));

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ExecutorService taskExecutor = Executor.getInstance().getExecutor();
		taskExecutor.shutdown();
		System.out.println("\n");
		initiator.send(StartingData.of(LocalDateTime.now(), "Nice game..."), bus);
		System.out.println(String.format("Game finished"));
		exit(0);
	}
}
