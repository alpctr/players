package com.alpctr.data;

import com.alpctr.players.AbstractDataType;
import com.alpctr.players.DataType;

/**
 * An event raised when a string message is sent.
 *
 */
public class MessageData extends AbstractDataType {

	private final String message;

	public MessageData(String message) {
		this.message = message;
	}

	public static DataType of(final String message) {
		return new MessageData(message);
	}

	public String getMessage() {
		return message;
	}

}
