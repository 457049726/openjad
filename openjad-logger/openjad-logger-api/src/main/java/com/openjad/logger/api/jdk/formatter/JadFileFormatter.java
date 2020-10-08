package com.openjad.logger.api.jdk.formatter;

import java.util.logging.LogRecord;
import java.util.logging.XMLFormatter;

public class JadFileFormatter extends XMLFormatter {

	private JadLogFormatter jadLogFormatter = new JadLogFormatter();

	public synchronized String format(LogRecord record) {
		String msg = super.format(record);
		return jadLogFormatter.format(record,msg);
	
	}

}
