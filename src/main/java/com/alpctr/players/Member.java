package com.alpctr.players;

import java.util.function.Consumer;

public interface Member extends Consumer<DataType> {

	  void accept(DataType event);

}
