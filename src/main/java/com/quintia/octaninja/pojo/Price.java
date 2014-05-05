package com.quintia.octaninja.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Price {
	private final Date timestamp;
	private final BigDecimal bid;
	private final BigDecimal ask;
	
	public Price(Date timestamp, BigDecimal bid, BigDecimal ask) {
		super();
		this.timestamp = timestamp;
		this.bid = bid;
		this.ask = ask;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public BigDecimal getBid() {
		return bid;
	}

	public BigDecimal getAsk() {
		return ask;
	}
}
