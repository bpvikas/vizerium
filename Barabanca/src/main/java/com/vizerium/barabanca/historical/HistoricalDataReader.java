package com.vizerium.barabanca.historical;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.io.FileUtils;

public class HistoricalDataReader {

	private static final Logger logger = Logger.getLogger(HistoricalDataReader.class);

	private static final String extractedDataDirectoryPath = FileUtils.directoryPath + "underlying-raw-data-v2-extract/";

	public List<UnitPriceData> getUnitPriceDataForRange(String scripName, LocalDateTime startDateTime, LocalDateTime endDateTime, TimeFormat timeFormat) {
		File timeSeriesForScripDirectory = new File(extractedDataDirectoryPath + timeFormat.getProperty() + "/" + scripName + "/");
		if (!timeSeriesForScripDirectory.exists()) {
			throw new RuntimeException(timeFormat.getProperty() + " data file does not exist for " + scripName);
		}

		if (endDateTime.isBefore(startDateTime)) {
			throw new RuntimeException("endDateTime " + endDateTime + " cannot be before startDateTime " + startDateTime);
		}

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(2);
		nf.setMaximumIntegerDigits(2);

		int startDateFilter = Integer.parseInt(String.valueOf(startDateTime.getYear()) + nf.format(startDateTime.getMonthValue()));
		int endDateFilter = Integer.parseInt(String.valueOf(endDateTime.getYear()) + nf.format(endDateTime.getMonthValue()));

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
}
