package com.quintia.octaninja.io;

import com.quintia.octaninja.exception.TradeException;

public interface CSVReadableI<T> {
	public T fromCSVLine(String line) throws TradeException;
}
