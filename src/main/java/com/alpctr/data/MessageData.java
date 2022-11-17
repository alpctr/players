package com.alpctr.data;

import com.alpctr.players.AbstractDataType;
import com.alpctr.players.DataType;

public class MessageData extends AbstractDataType {

	private final String message;

	public MessageData(String message) {
		super();
		this.message = message;
	}

	public static DataType of(final String message) {
		return new MessageData(message);
	}

	public String getMessage() {
		return message;
	}

}
