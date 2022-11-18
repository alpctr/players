package com.alpctr.event;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import com.alpctr.players.DataType;
import com.alpctr.players.Member;

public class Dispatcher {

	private final ThreadLocal<Queue<Event>> queue = new ThreadLocal<Queue<Event>>() {
		@Override
		protected Queue<Event> initialValue() {
			return new ArrayDeque<Event>();
		}
	};

	private final ThreadLocal<Boolean> dispatching = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return false;
		}
	};

	public void dispatch(DataType event, Iterator<Member> listeners) {
		Queue<Event> queueForThread = queue.get();
		queueForThread.offer(new Event(event, listeners));

		if (!dispatching.get()) {
			dispatching.set(true);
			try {
				Event nextEvent;
				while ((nextEvent = queueForThread.poll()) != null) {
					while (nextEvent.subscribers.hasNext()) {
						nextEvent.subscribers.next().accept(nextEvent.payload);
					}
				}
			} finally {
				dispatching.remove();
				queue.remove();
			}
		}
	}

	private static final class Event {
		private final DataType payload;
		private final Iterator<Member> subscribers;

		private Event(DataType event, Iterator<Member> listeners) {
			this.payload = event;
			this.subscribers = listeners;
		}
	}
}
