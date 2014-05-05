package com.quintia.octaninja;

import java.math.BigDecimal;
import java.util.Date;

import com.quintia.octaninja.exception.TradeException;
import com.quintia.octaninja.pojo.ExecutedTrade;
import com.quintia.octaninja.pojo.Price;
import com.quintia.octaninja.pojo.Trade;
import com.quintia.octaninja.pojo.TradeFactory;

import junit.framework.TestCase;

public class OrderMatcherTest extends TestCase {

	private Trade sellTrade1;
	private Trade sellTrade2;
	private Trade sellTrade3;

	private Trade buyTrade1;
	private Trade buyTrade2;
	private Trade buyTrade3;

	public OrderMatcherTest() {
		setupTrades();
	}
	
	public void testNoTrades() {
		OrderMatcher matcher = new OrderMatcher();
		Price price = new Price(new Date(),new BigDecimal("100.0"), new BigDecimal("105.0"));
		ExecutedTrade[] trades = matcher.getMatches(price);
		assertNotNull(trades);
		assertEquals(0, trades.length);
	}
	
	public void testPricesTooWide() {
		OrderMatcher matcher = new OrderMatcher();
		populateMatcher(matcher);
		Price price = new Price(new Date(),new BigDecimal("10.0"), new BigDecimal("673.0"));
		ExecutedTrade[] trades = matcher.getMatches(price);
		assertNotNull(trades);
		assertEquals(0, trades.length);
	}

	public void testSingleBuyMatches() {
		OrderMatcher matcher = new OrderMatcher();
		populateMatcher(matcher);
		Price price = new Price(new Date(),new BigDecimal("10.0"), new BigDecimal("140.00"));
		ExecutedTrade[] trades = matcher.getMatches(price);
		assertNotNull(trades);
		assertEquals(1, trades.length);
		assertSame(price, trades[0].getPrice());
		assertEquals(3L, trades[0].getTrade().getId());
		
		// Verify matched trade was removed from map
		assertEquals(5, matcher.getTradeCount());
		trades = matcher.getMatches(price);
		assertEquals(0, trades.length);
	}

	public void testMultipleBuysMatch() {
		OrderMatcher matcher = new OrderMatcher();
		populateMatcher(matcher);
		Price price = new Price(new Date(),new BigDecimal("10.0"), new BigDecimal("90.00"));
		ExecutedTrade[] trades = matcher.getMatches(price);
		assertNotNull(trades);
		assertEquals(2, trades.length);
		assertFalse(2L==trades[0].getTrade().getId());
		assertFalse(2L==trades[1].getTrade().getId());
		
		// Verify matched trade was removed from map
		assertEquals(4, matcher.getTradeCount());
		trades = matcher.getMatches(price);
		assertEquals(0, trades.length);
	}
	
	public void testBuyAndSellMatch() {
		OrderMatcher matcher = new OrderMatcher();
		populateMatcher(matcher);
		Price price = new Price(new Date(),new BigDecimal("61.0"), new BigDecimal("140.00"));
		ExecutedTrade[] trades = matcher.getMatches(price);
		assertNotNull(trades);
		assertEquals(2, trades.length);
		assertEquals(3L,trades[0].getTrade().getId()); // Buy trade
		assertEquals(220L,trades[1].getTrade().getId()); // Sell trade
		
		// Verify matched trade was removed from map
		assertEquals(4, matcher.getTradeCount());
		trades = matcher.getMatches(price);
		assertEquals(0, trades.length);
	}
	
	private void populateMatcher(OrderMatcher matcher) {
		matcher.addTrade(buyTrade1);
		matcher.addTrade(buyTrade2);
		matcher.addTrade(buyTrade3);
		matcher.addTrade(sellTrade1);
		matcher.addTrade(sellTrade2);
		matcher.addTrade(sellTrade3);
	}
	
	private void setupTrades() {
		try {
			TradeFactory fac = new TradeFactory();
			
			buyTrade1=fac.fromCSVLine("1,\"Buy\",100.0,10.0");
			buyTrade2=fac.fromCSVLine("2,\"Buy\",100.0,20.0");
			buyTrade3=fac.fromCSVLine("3,\"Buy\",150.0,5.0");	

			sellTrade1=fac.fromCSVLine("220,\"Sell\",50.0,10.0");
			sellTrade2=fac.fromCSVLine("1221,\"Sell\",60.0,5.0");
			sellTrade3=fac.fromCSVLine("1222,\"Sell\",90.0,20.0");
		} catch (TradeException tX) {
			fail();
		}
	}
}
