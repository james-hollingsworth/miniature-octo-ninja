package com.quintia.octaninja.pojo;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.TestCase;

public class TradeTest extends TestCase {
	public void testConstruction() {
		long id = 1L;
		TradeDirection direction = TradeDirection.BUY;
		BigDecimal limitPrice = new BigDecimal("100.0");
		BigDecimal margin = new BigDecimal("20.0");
		
		Trade trade = new Trade(id, direction, limitPrice, margin);
		assertEquals(id, trade.getId());
		assertEquals(direction, trade.getDirection());
		assertEquals(limitPrice, trade.getLimitPrice());
		assertEquals(margin, trade.getMargin());
	}
	
	public void testExecutionThreshold() {
		long id = 1L;
		TradeDirection direction = TradeDirection.BUY;
		BigDecimal limitPrice = new BigDecimal("100.0");
		BigDecimal margin = new BigDecimal("20.0");
		
		// Test for a buy trade
		Trade buyTrade = new Trade(id, direction, limitPrice, margin);
		assertEquals(new BigDecimal("80.0"), buyTrade.getExecutionThreshold());
		
		// Test for a sell trade
		direction = TradeDirection.SELL;
		Trade sellTrade = new Trade(id, direction, limitPrice, margin);
		assertEquals(new BigDecimal("120.0"), sellTrade.getExecutionThreshold());
	}
}
