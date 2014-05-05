package com.quintia.octaninja.pojo;

import java.math.BigDecimal;
import java.util.StringTokenizer;

import com.quintia.octaninja.exception.TradeException;
import com.quintia.octaninja.io.CSVReadableI;

public class TradeFactory implements CSVReadableI<Trade> {	
	public static final String CSV_DELIMITER=",";
	private static final int TRADE_FIELD_COUNT=4;
	
	@Override
	public Trade fromCSVLine(String line) throws TradeException {
		StringTokenizer st = new StringTokenizer(line,CSV_DELIMITER);
		
		final int tokenCount=st.countTokens();
		if (TRADE_FIELD_COUNT!=tokenCount) {
			throw new TradeException("Invalid CSV line. Expected fields: "+TRADE_FIELD_COUNT+" Actual: "+tokenCount);
		}
		
		try {
			return new Trade(Long.parseLong(st.nextToken()), 		// id
							 TradeDirection.valueOf(st.nextToken().replaceAll("[\"\']", "").toUpperCase()), // direction
							 new BigDecimal(st.nextToken()), 		// Limit Price
							 new BigDecimal(st.nextToken()) 		// Amount
				);
		} catch (NumberFormatException nX) {
			throw new TradeException("Unable to Parse CSV line: "+nX.getMessage());
		}
	}
}
