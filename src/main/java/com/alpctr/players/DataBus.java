package com.alpctr.players;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.alpctr.event.Dispatcher;


public class DataBus {

	private final Dispatcher dispatcher = new Dispatcher();
	private static final DataBus INSTANCE = new DataBus();

	//private final Set<Member> listeners = new HashSet<>();
    private final CopyOnWriteArraySet<Member> listeners = new CopyOnWriteArraySet<>();

	public static DataBus getInstance() {
		return INSTANCE;
	}

	/**
	 * Register a member with the data-bus to start receiving events.
	 *
	 * @param member The member to register
	 */
	public void subscribe(Member member) {
		this.listeners.add(member);
	}

	/**
	 * Deregister a member to stop receiving events.
	 *
	 * @param member The member to deregister
	 */
	public void unsubscribe(final Member member) {
		this.listeners.remove(member);
	}

	/**
	 * Publish and event to all members.
	 *
	 * @param event The event
	 */
	public void publish(final DataType event, Member sender) {
		event.setDataBus(this);
	    Predicate<Member> predicate = (member) -> member.equals(sender);
		
        Set<Member> listeners = this.listeners.stream()
                .filter(predicate.negate()::test)
                .collect(Collectors.toSet());
        
        
        //listeners.iterator().next().accept(event);
		//listeners.forEach(listener -> listener.accept(event));
		
		
		dispatcher.dispatch(event, listeners.iterator());
	}
}
