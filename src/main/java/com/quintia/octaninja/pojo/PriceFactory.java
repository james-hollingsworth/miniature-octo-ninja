package com.quintia.octaninja.pojo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import com.quintia.octaninja.exception.TradeException;
import com.quintia.octaninja.io.CSVReadableI;

public class PriceFactory implements CSVReadableI<Price> {	
	public static final String CSV_DELIMITER=",";
	private static final int PRICE_FIELD_COUNT=3;
	
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	@Override
	public Price fromCSVLine(String line) throws TradeException {
		StringTokenizer st = new StringTokenizer(line,CSV_DELIMITER);
		
		final int tokenCount=st.countTokens();
		if (PRICE_FIELD_COUNT!=tokenCount) {
			throw new TradeException("Invalid CSV line. Expected fields: "+PRICE_FIELD_COUNT+" Actual: "+tokenCount);
		}
		
		try {
			Date timestamp=null;
			synchronized(DATE_FORMATTER) {
				timestamp=DATE_FORMATTER.parse(st.nextToken().replaceAll("[\"\']", ""));
			}
			return new Price(timestamp,
							 new BigDecimal(st.nextToken()), 		// Limit Price
							 new BigDecimal(st.nextToken()) 		// Amount
				);
		} catch (ParseException pX) {
			throw new TradeException("Unable to Parse CSV line: "+pX.getMessage());
		} catch (NumberFormatException nX) {
			throw new TradeException("Unable to Parse CSV line: "+nX.getMessage());
		}
	}
}
