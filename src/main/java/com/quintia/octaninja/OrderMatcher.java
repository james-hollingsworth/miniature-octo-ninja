package com.quintia.octaninja;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.quintia.octaninja.pojo.ExecutedTrade;
import com.quintia.octaninja.pojo.Price;
import com.quintia.octaninja.pojo.Trade;
import com.quintia.octaninja.pojo.TradeDirection;

/**
 * Main matching logic backed by two TreeMaps - one for buy trades, another for sell trades
 * @author james
 *
 */
public class OrderMatcher {
	private final NavigableMap<BigDecimal, Trade> buyTradeMap = new TreeMap<>();
	private final NavigableMap<BigDecimal, Trade> sellTradeMap = new TreeMap<>();
	
	public void addTrade(Trade trade) {
		if (TradeDirection.BUY==trade.getDirection()) {
			buyTradeMap.put(trade.getExecutionThreshold(), trade);
		} else {
			sellTradeMap.put(trade.getExecutionThreshold(), trade);
		}
	}
	
	public void clear() {
		buyTradeMap.clear();
		sellTradeMap.clear();
	}
	
	public ExecutedTrade[] getMatches(Price price) {
		final List<ExecutedTrade> matchList = new ArrayList<>();
		
		// First get matching buy trades
		synchronized(buyTradeMap) {
			Entry<BigDecimal,Trade> match = buyTradeMap.ceilingEntry(price.getAsk());
			while (match!=null) {
				matchList.add(new ExecutedTrade(match.getValue(),price));
				buyTradeMap.remove(match.getKey());
				match = buyTradeMap.ceilingEntry(price.getAsk());
			}
		}
		// Then get matching sell trades
		synchronized(sellTradeMap) {
			Entry<BigDecimal,Trade> match = sellTradeMap.floorEntry(price.getBid());
			while (match!=null) {
				matchList.add(new ExecutedTrade(match.getValue(),price));
				sellTradeMap.remove(match.getKey());
				match = sellTradeMap.floorEntry(price.getBid());
			}
		}
		
		return matchList.toArray(new ExecutedTrade[matchList.size()]); 
	}
	
	int getTradeCount() {
		return buyTradeMap.size() + sellTradeMap.size();
	}
}
