package com.quintia.octaninja.pojo;

import java.math.BigDecimal;

import com.quintia.octaninja.exception.TradeException;

import junit.framework.TestCase;

public class TradeFactoryTest extends TestCase {
	public void testCSVConstruction() {
		TradeFactory fac = new TradeFactory();
		
		// Basic CSV line 
		String csvLine="15,\"Buy\",628.28920,20.0000";
		// Expected results
		long id = 15L;
		TradeDirection direction = TradeDirection.BUY;
		BigDecimal limitPrice = new BigDecimal("628.28920");
		BigDecimal margin = new BigDecimal("20.0000");
		
		try {
			Trade trade = fac.fromCSVLine(csvLine);
			assertEquals(id, trade.getId());
			assertEquals(direction, trade.getDirection());
			assertEquals(limitPrice, trade.getLimitPrice());
			assertEquals(margin, trade.getMargin());			
		} catch (TradeException tX) {
			fail();
		}
	}
}
