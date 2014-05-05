package com.quintia.octaninja.pojo;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.TestCase;

public class PriceTest extends TestCase {
	public void testConstruction() {
		Date timestamp = new Date();
		BigDecimal bid = new BigDecimal("1.0");
		BigDecimal ask = new BigDecimal("2.0");
		
		Price price = new Price(timestamp,bid,ask);
		assertSame(timestamp, price.getTimestamp());
		assertEquals(bid, price.getBid());
		assertEquals(ask, price.getAsk());
	}	
}
