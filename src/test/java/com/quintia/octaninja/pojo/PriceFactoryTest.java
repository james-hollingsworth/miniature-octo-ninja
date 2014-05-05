package com.quintia.octaninja.pojo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.quintia.octaninja.exception.TradeException;

import junit.framework.TestCase;

public class PriceFactoryTest extends TestCase {
	public void testCSVConstruction() {
		PriceFactory fac = new PriceFactory();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// Basic CSV line 
		String csvLine="\"2014-04-11 02:25:04\",340.00000000,348.11000000";
		// Expected results
		Date timestamp = null;
		try {
			timestamp = formatter.parse("2014-04-11 02:25:04");
		} catch (ParseException pX) {
			fail();
		}
		BigDecimal bid = new BigDecimal("340.00000000");
		BigDecimal ask = new BigDecimal("348.11000000");
		
		try {
			Price price = fac.fromCSVLine(csvLine);
			assertEquals(timestamp, price.getTimestamp());
			assertEquals(bid, price.getBid());
			assertEquals(ask, price.getAsk());
		} catch (TradeException tX) {
			fail();
		}
	}
}
