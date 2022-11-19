package com.alpctr.players;

import static java.lang.System.exit;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import com.alpctr.data.MessageData;
import com.alpctr.member.Executor;
import com.alpctr.member.Player;

class App {

	public static void main(String[] args) {
		String message = "Knock knock!";
		Player initiator;
		final var bus = DataBus.getInstance();
		
		CountDownLatch latch = new CountDownLatch(18);
		
		Player player1 = new Player("player1");
		Player player2 = new Player("player2");
		
		player1.setLatch(latch);
		player2.setLatch(latch);

		initiator = player1;

		bus.subscribe(player1);
		bus.subscribe(player2);

		System.out.println(String.format("Sent message: %s in %s", message, initiator.getName()));
		
		initiator.getSendCounter().incrementAndGet();
		bus.publish(MessageData.of(message), initiator);
		
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ExecutorService taskExecutor = Executor.getInstance().getExecutor();
		
		taskExecutor.shutdown();
		

		System.out.println(String.format("Finished"));
		exit(0);
		
	}
}
