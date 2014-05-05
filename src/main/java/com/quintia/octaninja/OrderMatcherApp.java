package com.quintia.octaninja;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.quintia.octaninja.exception.TradeException;
import com.quintia.octaninja.io.CSVReader;
import com.quintia.octaninja.pojo.Price;
import com.quintia.octaninja.pojo.PriceFactory;
import com.quintia.octaninja.pojo.Trade;
import com.quintia.octaninja.pojo.TradeFactory;

public class OrderMatcherApp 
{
	// Should really go in a properties file
	private static final String PRICE_FILE = "prices.csv";
	private static final String TRADE_FILE = "trades.csv";
	private static final int MATCH_WORKERS = 2;

	private final Logger logger = Logger.getLogger(OrderMatcherApp.class);
	
	private final OrderMatcher matcher = new OrderMatcher();
	
	private final BlockingQueue<Price> priceQueue = new LinkedBlockingQueue<>();
	
	private final MatchWorker[] matchWorkers = new MatchWorker[MATCH_WORKERS];
	
	public OrderMatcherApp() {
		
	}
	
	public void start() throws TradeException {
		long startTime = System.currentTimeMillis();
		
		loadTrades();
		
		for (int i=0; i<MATCH_WORKERS; i++) {
			matchWorkers[i]=new MatchWorker(priceQueue, matcher);
		}
		
		matchPrices();
		
		long runDuration = System.currentTimeMillis()-startTime;
		logger.info("Execution took "+runDuration+"ms");
		
		// Stop workers
		for (int i=0; i<MATCH_WORKERS; i++) {
			matchWorkers[i].stopWorker();
		}
	}
	
	
	/**
	 * Loads all trades into memory.
	 * For a larger data set we would instead need
	 * to process each sequentially.
	 */
	private void loadTrades() throws TradeException {
		matcher.clear();
		CSVReader<Trade> reader = new CSVReader<>(TRADE_FILE, new TradeFactory());
		try {
			reader.openReader();
			Trade trade = reader.readNext();
			while (null!=trade) {
				matcher.addTrade(trade);
				trade = reader.readNext();
			}
		} catch (IOException iX) {
			throw new TradeException("Unable to open file "+TRADE_FILE);
		} finally {
			try {
				reader.closeReader();
			} catch (IOException iX) {
				logger.error("Unable to close reader");
			}
		}
	}
	
	private void matchPrices() throws TradeException {
		CSVReader<Price> reader = new CSVReader<>(PRICE_FILE, new PriceFactory());
		try {
			reader.openReader();
			Price price = reader.readNext();
			while (null!=price) {
				priceQueue.add(price);
				price = reader.readNext();
			}
		} catch (IOException iX) {
			throw new TradeException("Unable to open file "+TRADE_FILE);
		} finally {
			try {
				reader.closeReader();
			} catch (IOException iX) {
				logger.error("Unable to close reader");
			}
		}		
	}
	
    public static void main( String[] args )
    {
    	//PropertiesConfigurator is used to configure logger from properties file
    	PropertyConfigurator.configure("log4j.properties");
    	
        OrderMatcherApp app = new OrderMatcherApp();
        try {
        	app.start();
        } catch (TradeException tX) {
        	System.out.println("Unable to run application: "+tX.getMessage());
        }
    }
}
