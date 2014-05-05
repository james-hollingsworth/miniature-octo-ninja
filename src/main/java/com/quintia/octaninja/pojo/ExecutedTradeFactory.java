package com.quintia.octaninja.pojo;

public class ExecutedTradeFactory {
	public static ExecutedTrade createExecutedTrade(Trade trade, Price price) {
		return new ExecutedTrade(trade, price);
	}
}
