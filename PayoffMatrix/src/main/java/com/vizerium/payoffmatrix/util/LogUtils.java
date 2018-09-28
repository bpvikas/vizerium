package com.vizerium.payoffmatrix.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtils {

	public static void initializeLogging(String strategyName) {
		String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		System.setProperty("application.run.datetime", dateString);
		System.setProperty("application.strategy.name", strategyName);
	}
}
