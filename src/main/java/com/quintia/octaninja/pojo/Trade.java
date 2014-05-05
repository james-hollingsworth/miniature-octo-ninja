package com.quintia.octaninja.pojo;

import java.math.BigDecimal;

public class Trade {

	private final long id;
	private final TradeDirection direction;
	private final BigDecimal limitPrice;
	private final BigDecimal margin;
	
	public Trade(long id, TradeDirection direction, BigDecimal limitPrice,
			BigDecimal amount) {
		super();
		this.id = id;
		this.direction = direction;
		this.limitPrice = limitPrice;
		this.margin = amount;
	}

	public long getId() {
		return id;
	}

	public TradeDirection getDirection() {
		return direction;
	}

	public BigDecimal getLimitPrice() {
		return limitPrice;
	}

	public BigDecimal getMargin() {
		return margin;
	}
	
	/**
	 * Returns the minimum or maximum price (depending on whether trade is BUY or SELL)
	 * that the trade will execute at
	 * @return
	 */
	public BigDecimal getExecutionThreshold() {
		return TradeDirection.BUY==getDirection() ? getLimitPrice().subtract(getMargin()) : getLimitPrice().add(getMargin());
	}
}
