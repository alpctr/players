package com.alpctr.event;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.Flow.Subscriber;

import com.alpctr.players.DataType;
import com.alpctr.players.Member;

public class Dispatcher {

    private final ThreadLocal<Queue<Event>> queue = ThreadLocal.withInitial(ArrayDeque::new);

    private final ThreadLocal<Boolean> dispatching = ThreadLocal.withInitial(() -> false);

    public void dispatch(DataType event, Iterator<Member> listeners) {
        Queue<Dispatcher.Event> queueForThread = queue.get();
        queueForThread.offer(new Dispatcher.Event(event, listeners));

        if (!dispatching.get()) {
            dispatching.set(true);
            try {
            	Dispatcher.Event nextEvent;
                while ((nextEvent = queueForThread.poll()) != null) {

                    for(;nextEvent.listeners.hasNext();){
                        nextEvent.listeners.next().accept(nextEvent.payload);
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
        private final Iterator<Member> listeners;

        private Event(DataType event, Iterator<Member> listeners) {
            this.payload = event;
            this.listeners = listeners;
        }
    }
}
