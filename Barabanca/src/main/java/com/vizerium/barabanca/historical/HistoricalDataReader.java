package com.vizerium.barabanca.historical;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.io.FileUtils;
import com.vizerium.commons.util.NumberFormats;

public class HistoricalDataReader {

	private static final Logger logger = Logger.getLogger(HistoricalDataReader.class);

	private static final NumberFormat mnf = NumberFormats.getForMonth();

	private static final String extractedDataDirectoryPath = FileUtils.directoryPath + "underlying-raw-data-v2-extract/";

	public List<UnitPriceData> getUnitPriceDataForRange(String scripName, LocalDateTime startDateTime, LocalDateTime endDateTime, TimeFormat timeFormat) {
		File timeSeriesForScripDirectory = new File(extractedDataDirectoryPath + timeFormat.getProperty() + "/" + scripName + "/");
		if (!timeSeriesForScripDirectory.exists()) {
			throw new RuntimeException(timeFormat.getProperty() + " data file does not exist for " + scripName);
		}

		if (endDateTime.isBefore(startDateTime)) {
			throw new RuntimeException("endDateTime " + endDateTime + " cannot be before startDateTime " + startDateTime);
		}

		int startDateFilter = Integer.parseInt(String.valueOf(startDateTime.getYear()) + mnf.format(startDateTime.getMonthValue()));
		int endDateFilter = Integer.parseInt(String.valueOf(endDateTime.getYear()) + mnf.format(endDateTime.getMonthValue()));

		File[] filesEligibletoReturnData = null;
		if (timeFormat.getInterval() > 0) {
			FilenameFilter fileNameFilter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					int fileDate = Integer.parseInt(name.substring(scripName.length(), name.lastIndexOf('.')));
					if (fileDate >= startDateFilter && fileDate <= endDateFilter) {
						return true;
					}
					return false;
				}
			};
			filesEligibletoReturnData = timeSeriesForScripDirectory.listFiles(fileNameFilter);

		} else {
			filesEligibletoReturnData = new File[] { new File(extractedDataDirectoryPath + timeFormat.getProperty() + "/" + scripName + "/" + scripName + ".txt") };
		}

		List<UnitPriceData> unitPriceDataList = new ArrayList<UnitPriceData>();
		for (File eligibleFile : filesEligibletoReturnData) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(eligibleFile));
				String dataLine = null;
				while (StringUtils.isNotBlank(dataLine = br.readLine())) {
					String[] dataLineDetails = dataLine.split(",");
					UnitPriceData unitPriceData = new UnitPriceData(dataLineDetails);
					unitPriceData.setTimeFormat(timeFormat);
					if (timeFormat.getInterval() > 0 && !TimeFormat._1DAY.equals(timeFormat) && !unitPriceData.getDateTime().isBefore(startDateTime)
							&& !unitPriceData.getDateTime().isAfter(endDateTime)) {
						unitPriceDataList.add(unitPriceData);
					} else if ((timeFormat.getInterval() <= 0 || TimeFormat._1DAY.equals(timeFormat)) && !unitPriceData.getDate().isBefore(startDateTime.toLocalDate())
							&& !unitPriceData.getDate().isAfter(endDateTime.toLocalDate())) {
						unitPriceDataList.add(unitPriceData);
					}
				}
				br.close();
			} catch (IOException ioe) {
				logger.error("An I/O error occurred while filtering historical data.", ioe);
				throw new RuntimeException(ioe);
			}
		}
		return unitPriceDataList;
	}

	public List<UnitPriceData> getPreviousN(String scripName, TimeFormat timeFormat, LocalDateTime unitPriceDateTime, int count) {
		LocalDateTime startDateTime;
		LocalDateTime endDateTime = LocalDateTime.of(unitPriceDateTime.toLocalDate(), unitPriceDateTime.toLocalTime());

		if (timeFormat.getInterval() > 0) {
			int minusMonths = 1;
			if (timeFormat.equals(TimeFormat._1DAY)) {
				minusMonths = (count / 20) + 1; // assuming that there are approximately 20 odd days in a month.
			}
			startDateTime = unitPriceDateTime.minusMonths(minusMonths).withDayOfMonth(1);
			if (timeFormat.equals(TimeFormat._1MIN)) {
				endDateTime = endDateTime.minusMinutes(1);
			} else if (timeFormat.equals(TimeFormat._5MIN)) {
				endDateTime = endDateTime.minusMinutes(5);
			} else if (timeFormat.equals(TimeFormat._1HOUR)) {
				endDateTime = endDateTime.minusHours(1).withMinute(59);
			} else if (timeFormat.equals(TimeFormat._1DAY)) {
				endDateTime = endDateTime.minusDays(1).withHour(23).withMinute(59);
			}

		} else {
			if (timeFormat.equals(TimeFormat._1WEEK)) {
				endDateTime = unitPriceDateTime.minusWeeks(1).with(DayOfWeek.SATURDAY);
			} else if (timeFormat.equals(TimeFormat._1MONTH)) {
				endDateTime = unitPriceDateTime.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
			}
			startDateTime = HistoricalDataDateRange.getStartDateTime(scripName, timeFormat);
		}

		List<UnitPriceData> unitPriceDataList = getUnitPriceDataForRange(scripName, startDateTime, endDateTime, timeFormat);
		int size = unitPriceDataList.size();
		if (size >= count) {
			return unitPriceDataList.subList(size - count, size);
		} else {
			throw new RuntimeException("Unable to get previous " + count + " unit prices from a list of size " + size);
		}
	}

	public List<UnitPriceData> getNextN(String scripName, TimeFormat timeFormat, LocalDateTime unitPriceDateTime, int count) {
		LocalDateTime startDateTime = LocalDateTime.of(unitPriceDateTime.toLocalDate(), unitPriceDateTime.toLocalTime());
		LocalDateTime endDateTime;

		if (timeFormat.getInterval() > 0) {
			int plusMonths = 1;
			if (timeFormat.equals(TimeFormat._1DAY)) {
				plusMonths = (count / 20) + 1; // assuming that there are approximately 20 odd days in a month.
			}
			endDateTime = unitPriceDateTime.plusMonths(plusMonths).with(TemporalAdjusters.lastDayOfMonth());
			if (timeFormat.equals(TimeFormat._1MIN) || timeFormat.equals(TimeFormat._5MIN) || timeFormat.equals(TimeFormat._1HOUR)) {
				// The "1" below is not a typo, the calculations are such that for the next 5 minute candle to be returned,
				// we just need to move the current time by one minute.
				startDateTime = startDateTime.plusMinutes(1);
			} else if (timeFormat.equals(TimeFormat._1DAY)) {
				startDateTime = startDateTime.plusDays(1).withHour(0).withMinute(0);
			}
		} else {
			if (timeFormat.equals(TimeFormat._1WEEK)) {
				startDateTime = unitPriceDateTime.plusWeeks(1).with(DayOfWeek.MONDAY);
			} else if (timeFormat.equals(TimeFormat._1MONTH)) {
				startDateTime = unitPriceDateTime.plusMonths(1).withDayOfMonth(1);
			}
			endDateTime = HistoricalDataDateRange.getEndDateTime(scripName, timeFormat);
		}

		List<UnitPriceData> unitPriceDataList = getUnitPriceDataForRange(scripName, startDateTime, endDateTime, timeFormat);
		int size = unitPriceDataList.size();
		if (size >= count) {
			return unitPriceDataList.subList(0, count);
		} else {
			throw new RuntimeException("Unable to get next " + count + " unit prices from a list of size " + size);
		}
	}

	// This is to typically identify if current candle is the last one of the e.g. day/hour/week.
	// The end goal of this is that if you are not making a profit by the end of the day (for hourly chart)
	// or hour (for 5 min chart) or week (for daily chart), then close the trade.
	public boolean isLastCandleOfTimePeriod(UnitPriceData unitPrice, TimeFormat timeFormat) {
		if (unitPrice.getTimeFormat().equals(timeFormat)) {
			// If the timeFormat of the unitPrice is the same as the timeFormat supplied, then return true
			// For example, if you have a Day unitPrice, then that will be the only candle for that Day.
			return true;
		} else if (unitPrice.getTimeFormat().isHigherTimeFormatThan(timeFormat)) {
			throw new RuntimeException("The timeFormat of the unitPrice is higher than the supplied timeFormat.");
		} else {
			UnitPriceData suppliedTimeFormatNextUnitPrice = getNextN(unitPrice.getScripName(), timeFormat, unitPrice.getDateTime(), 1).get(0);
			UnitPriceData sameTimeFormatNextUnitPrice = getNextN(unitPrice.getScripName(), unitPrice.getTimeFormat(), unitPrice.getDateTime(), 1).get(0);
			if (sameTimeFormatNextUnitPrice.getDateTime().equals(suppliedTimeFormatNextUnitPrice.getDateTime())) {
				return true;
			} else {
				return false;
			}
		}
	}
}