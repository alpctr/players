package com.alpctr.players;

import com.alpctr.data.MessageData;
import com.alpctr.member.Player;

class App {

	public static void main(String[] args) {
		String message = "Peace";
		Player initiator;
		final var bus = DataBus.getInstance();

		Player player1 = new Player("player1");
		Player player2 = new Player("player2");

		initiator = player1;

		bus.subscribe(player1);
		bus.subscribe(player2);

		System.out.println(String.format("Sent message: %s in %s", message, initiator.getName()));
		bus.publish(MessageData.of(message), initiator);

		/*
		 * bus.subscribe(new StatusMember(1)); bus.subscribe(new StatusMember(2)); final
		 * var foo = new MessageCollectorMember("Foo"); final var bar = new
		 * MessageCollectorMember("Bar"); bus.subscribe(foo);
		 * bus.publish(StartingData.of(LocalDateTime.now()));
		 * bus.publish(MessageData.of("Only Foo should see this")); bus.subscribe(bar);
		 * bus.publish(MessageData.of("Foo and Bar should see this"));
		 * bus.unsubscribe(foo);
		 * bus.publish(MessageData.of("Only Bar should see this"));
		 * bus.publish(StoppingData.of(LocalDateTime.now()));
		 */
	}
}
