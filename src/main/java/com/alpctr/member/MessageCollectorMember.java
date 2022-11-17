package com.alpctr.member;

import java.util.ArrayList;
import java.util.List;

import com.alpctr.data.MessageData;
import com.alpctr.players.DataType;
import com.alpctr.players.Member;

public class MessageCollectorMember implements Member {

	private final String name;

	private final List<String> messages = new ArrayList<>();

	public MessageCollectorMember(String name) {
		this.name = name;
	}

	@Override
	public void accept(final DataType data) {
		if (data instanceof MessageData) {
			handleEvent((MessageData) data);
		}
	}

	private void handleEvent(MessageData data) {
		System.out.println(String.format("%s sees message %s", name, data.getMessage()));
	    messages.add(data.getMessage());
	  }

	public List<String> getMessages() {
		return List.copyOf(messages);
	}
}