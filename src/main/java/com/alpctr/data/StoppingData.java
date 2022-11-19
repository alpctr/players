package com.alpctr.data;

import java.time.LocalDateTime;

import com.alpctr.players.AbstractDataType;
import com.alpctr.players.DataType;

public class StoppingData extends AbstractDataType {

	private final LocalDateTime when;
	private final String goodbye;

	public StoppingData(LocalDateTime when, String goodbye) {
		super();
		this.when = when;
		this.goodbye = goodbye;
	}

	public static DataType of(final LocalDateTime when, final String goodbye) {
		return new StoppingData(when, goodbye);
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public String getGoodbye() {
		return goodbye;
	}
}
