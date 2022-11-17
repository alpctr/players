package com.alpctr.data;

import java.time.LocalDateTime;

import com.alpctr.players.AbstractDataType;
import com.alpctr.players.DataType;

public class StartingData extends AbstractDataType {

	private final LocalDateTime when;

	public StartingData(LocalDateTime when) {
		super();
		this.when = when;
	}

	public static DataType of(final LocalDateTime when) {
		return new StartingData(when);
	}

	public LocalDateTime getWhen() {
		return when;
	}
}
