package com.quintia.octaninja.pojo;

import java.math.BigDecimal;

public class ExecutedTrade {
	private final Trade trade;
	private final Price price;
	
	public ExecutedTrade(Trade trade, Price price) {
		super();
		this.trade = trade;
		this.price = price;
	}
	
	public Trade getTrade() {
		return trade;
	}
	
	public Price getPrice() {
		return price;
	}
	
	public BigDecimal getExecutedPriceValue() {
		return (TradeDirection.BUY==getTrade().getDirection()) ? getPrice().getAsk() : getPrice().getBid();
	}
}
