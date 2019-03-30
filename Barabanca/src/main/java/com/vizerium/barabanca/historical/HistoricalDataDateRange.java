package com.vizerium.barabanca.historical;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.io.FileUtils;

public class HistoricalDataDateRange {

	private static final Logger logger = Logger.getLogger(HistoricalDataDateRange.class);

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

	private static Properties dateRangeProperties;

	private static final String propertyFilePath = FileUtils.directoryPath + "underlying-raw-data-v2-extract/" + "dateRange.properties";

	private static final String startDateTimeSuffix = ".StartDateTime";

	private static final String endDateTimeSuffix = ".EndDateTime";

	private static HistoricalDataDateRange instance = getInstance();

	public static synchronized HistoricalDataDateRange getInstance() {
		if (instance == null) {
			instance = new HistoricalDataDateRange();
		}
		return instance;
	}

	private HistoricalDataDateRange() {
		try {
			dateRangeProperties = new Properties() {
				private static final long serialVersionUID = 372365278256103704L;

				@Override
				public synchronized Enumeration<Object> keys() {
					return Collections.enumeration(new TreeSet<Object>(super.keySet()));
				}
			};

			dateRangeProperties.load(new FileReader(propertyFilePath));
		} catch (IOException e) {
			logger.error("Unable to load historical data date ranges.", e);
			throw new RuntimeException(e);
		}
	}

	public static boolean setStartDateTime(String scripName, TimeFormat timeFormat, LocalDateTime startDateTime) {
		String startDateTimePropertyName = scripName + "." + timeFormat.getProperty() + startDateTimeSuffix;
		LocalDateTime currentStartDate = getDateTime(startDateTimePropertyName);
		if ((currentStartDate == null) || (currentStartDate != null) && startDateTime.isBefore(currentStartDate)) {
			return setDateTime(startDateTimePropertyName, startDateTime);
		} else {
			return false;
		}
	}

	public static boolean setEndDateTime(String scripName, TimeFormat timeFormat, LocalDateTime endDateTime) {
		String endDateTimePropertyName = scripName + "." + timeFormat.getProperty() + endDateTimeSuffix;
		LocalDateTime currentEndDateTime = getDateTime(endDateTimePropertyName);
		if ((currentEndDateTime == null) || (currentEndDateTime != null) && endDateTime.isAfter(currentEndDateTime)) {
			return setDateTime(endDateTimePropertyName, endDateTime);
		} else {
			return false;
		}
	}

	private static boolean setDateTime(String propertyName, LocalDateTime dateTime) {
		try {
			dateRangeProperties.setProperty(propertyName, dateTimeFormatter.format(dateTime));
			dateRangeProperties.store(new BufferedWriter(new FileWriter(propertyFilePath)), null);
		} catch (IOException e) {
			logger.error("Unable to write historical data date ranges to file.", e);
			throw new RuntimeException(e);
		}
		return true;
	}

	public static LocalDateTime getStartDateTime(String scripName, TimeFormat timeFormat) {
		LocalDateTime startDateTime = getDateTime(scripName + "." + timeFormat.getProperty() + startDateTimeSuffix);
		if (startDateTime != null) {
			return startDateTime;
		} else {
			throw new RuntimeException("Historical data start dateTime is not set for " + scripName + " " + timeFormat.getProperty());
		}
	}

	public static LocalDateTime getEndDateTime(String scripName, TimeFormat timeFormat) {
		LocalDateTime endDateTime = getDateTime(scripName + "." + timeFormat.getProperty() + endDateTimeSuffix);
		if (endDateTime != null) {
			return endDateTime;
		} else {
			throw new RuntimeException("Historical data end dateTime is not set for " + scripName + " " + timeFormat.getProperty());
		}
	}

	private static LocalDateTime getDateTime(String propertyName) {
		if (StringUtils.isNotBlank(dateRangeProperties.getProperty(propertyName))) {
			return LocalDateTime.parse(dateRangeProperties.getProperty(propertyName), dateTimeFormatter);
		} else {
			return null;
		}
	}
}