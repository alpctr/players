package com.alpctr.data;

import java.time.LocalDateTime;

import com.alpctr.players.AbstractDataType;
import com.alpctr.players.DataType;

public class StoppingData extends AbstractDataType {

	private final LocalDateTime when;

	public StoppingData(LocalDateTime when) {
		super();
		this.when = when;
	}

	public static DataType of(final LocalDateTime when) {
		return new StoppingData(when);
	}

	public LocalDateTime getWhen() {
		return when;
	}
}
