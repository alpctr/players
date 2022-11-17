package com.alpctr.players;

public interface Subscribable {
	
	void subscribe(DataBus dataBus);
	void unsubscribe(DataBus dataBus);

}
