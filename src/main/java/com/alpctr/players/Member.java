package com.alpctr.players;

import java.util.function.Consumer;

/**
 * Members receive events from the Data-Bus.
 */
public interface Member extends Consumer<DataType> {

	  void accept(DataType event);

}
