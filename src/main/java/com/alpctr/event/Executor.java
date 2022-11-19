package com.alpctr.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executes tasks comes from players
 */
public class Executor {
	private static final int NUMBER_OF_THREADS = 16;
	private final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
	private static final Executor INSTANCE = new Executor();
	
	public static Executor getInstance() {
		return INSTANCE;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

}
