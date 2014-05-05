package com.quintia.octaninja;

import java.text.SimpleDateFormat;
import java.util.concurrent.BlockingQueue;

import com.quintia.octaninja.pojo.ExecutedTrade;
import com.quintia.octaninja.pojo.Price;

/**
 * A single worker which consumes from the price queue
 * and attempts to locate matching trades for the consumed price
 * 
 * @author james
 */
public class MatchWorker extends Thread {

	// USed in a thread safe manner - i.e. only by this thread
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
    private final BlockingQueue<Price> priceQueue;
    private final OrderMatcher matcher;
    
    
    private volatile boolean shouldRun=true;

    public MatchWorker(BlockingQueue<Price> queue, OrderMatcher matcher) {
        this.priceQueue = queue;
        this.matcher = matcher;
        start();
    }
    
    public void stopWorker() {
    	this.shouldRun=false;
    	this.interrupt();
    }

    /**
     * Display details of the matched trades
     * @param trades
     */
    private void dumpMatches(ExecutedTrade[] trades) {    	
		for (ExecutedTrade trade : trades) {
			String timestampStr=null;
			timestampStr=DATE_FORMATTER.format(trade.getPrice().getTimestamp());
			String output = String.format("%6d %s %s", trade.getTrade().getId(),
			                                           timestampStr,
			                                           trade.getExecutedPriceValue().toString());

			// I was tempted to use Log4J here, but thought it best to adhere to the output specification 
			System.out.println(output);
		}    	
    }
    
    public void run() {
        try {
        	while(this.shouldRun) {
        		Price price = priceQueue.take();
        		ExecutedTrade[] trades = matcher.getMatches(price);
        		dumpMatches(trades);
        	}
        } catch (InterruptedException e) {
            // Expected
        }
    }
}
