package com.alpctr.member;

import java.time.LocalDateTime;

import com.alpctr.data.MessageData;
import com.alpctr.data.StartingData;
import com.alpctr.data.StoppingData;
import com.alpctr.players.DataType;
import com.alpctr.players.Member;

public class StatusMember implements Member {

	  private final int id;

	  private LocalDateTime started;

	  private LocalDateTime stopped;
	  
	  public StatusMember(int id) {
		super();
		this.id = id;
	}

	@Override
	  public void accept(final DataType data) {
	    if (data instanceof StartingData) {
	      handleEvent((StartingData) data);
	    } else if (data instanceof StoppingData) {
	      handleEvent((StoppingData) data);
	    }
	  }

	  private void handleEvent(StartingData data) {
	    started = data.getWhen();
	    System.out.println(String.format("Receiver %d sees application started at %s", id, started.toString()));
	  }

	  private void handleEvent(StoppingData data) {
	    stopped = data.getWhen();
	    System.out.println(String.format("Receiver %d sees application stopping at %s", id, stopped.toString()));
	    System.out.println(String.format("Receiver %d sending goodbye message", id));
	    data.getDataBus().publish(MessageData.of(String.format("Goodbye cruel world from #%d!", id)), null);
	  }

	}