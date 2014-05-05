package com.quintia.octaninja.io;

import java.io.IOException;

import com.quintia.octaninja.exception.TradeException;
import com.quintia.octaninja.pojo.Trade;

public interface OMReaderI<T> {
	
	/**
	 * Initiate reading from the file or stream
	 */
	public void openReader() throws IOException;
	
	/**
	 * Closer file or stream and tidy up
	 */
	public void closeReader() throws IOException;
	
	/**
	 * Read next Trade from file or stream.  Returns null if end of file or stream
	 * has ben reached
	 * @return
	 */
	public T readNext() throws TradeException;
}
