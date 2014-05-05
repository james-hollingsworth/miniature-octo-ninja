package com.quintia.octaninja.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import com.quintia.octaninja.exception.TradeException;
import com.quintia.octaninja.pojo.Trade;
import com.quintia.octaninja.pojo.TradeFactory;

public class CSVReader<T> implements OMReaderI<T> {

	private final String filename;	
	private final CSVReadableI<T> factory;
	private BufferedReader bufReader;
	
	public CSVReader(String filename, CSVReadableI<T> factory) {
		this.filename = filename;
		this.factory = factory;
	}
	
	@Override
	public T readNext() throws TradeException {
		try {
			String line = bufReader.readLine();
			if (null != line) {
				return factory.fromCSVLine(line);
			} else {
				// Reached end of file
				return null;
			}
		} catch (IOException iX) {
			throw new TradeException("IOException during read of file: "+iX.getMessage());
		}
	}

	@Override
	public void openReader() throws IOException {
		File tradeFile = new File(this.filename);
		bufReader  = new BufferedReader(new FileReader(tradeFile));
	}

	@Override
	public void closeReader() throws IOException {
		bufReader.close();
	}
}
