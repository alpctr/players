package com.alpctr.data;

import java.time.LocalDateTime;

import com.alpctr.players.AbstractDataType;
import com.alpctr.players.DataType;
/**
 * An event raised when applications starts, containing the start time of the application and a greeting message from initiator.
 *
 */
public class StartingData extends AbstractDataType {

	private final LocalDateTime when;
	private final String greeting;

	public StartingData(LocalDateTime when, String greeting) {
		super();
		this.when = when;
		this.greeting = greeting;
	}

	public static DataType of(final LocalDateTime when, final String greeting) {
		return new StartingData(when, greeting);
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public String getGreeting() {
		return greeting;
	}
}
