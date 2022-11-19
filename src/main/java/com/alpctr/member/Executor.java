package com.alpctr.member;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {
	private final ExecutorService executor = Executors.newFixedThreadPool(16);
	private static final Executor INSTANCE = new Executor();
	
	public static Executor getInstance() {
		return INSTANCE;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

}
