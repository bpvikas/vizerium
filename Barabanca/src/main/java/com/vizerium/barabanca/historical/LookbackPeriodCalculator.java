/*
 * Copyright 2019 Vizerium, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vizerium.barabanca.historical;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.dao.UnitPriceData;
import com.vizerium.commons.indicators.Indicator;

public class LookbackPeriodCalculator<I extends Indicator<I>> {

	private HistoricalDataReader historicalDataReader;

	public LookbackPeriodCalculator() {
		historicalDataReader = new HistoricalDataReader();
	}

	public int getLookbackPeriodForIndicator(I indicator) {
		return indicator.getTotalLookbackPeriodRequiredToRemoveBlankIndicatorDataFromInitialValues();
	}

	public List<UnitPriceData> getUnitPricesIncludingLookbackPeriodWithTimeFormat(TimeFormat outputTimeFormat, List<UnitPriceData> unitPriceDataList, I indicator) {
		return getUnitPricesIncludingLookbackPeriodWithTimeFormat(outputTimeFormat, unitPriceDataList, getLookbackPeriodForIndicator(indicator));
	}

	public List<UnitPriceData> getUnitPricesIncludingLookbackPeriodWithTimeFormat(TimeFormat outputTimeFormat, List<UnitPriceData> unitPriceDataList, int lookbackPeriod) {

		if (unitPriceDataList.isEmpty()) {
			throw new RuntimeException("Unable to calculate lookback period for no unit price data.");
		}
		String scripName = unitPriceDataList.get(0).getScripName();
		DateTimeTuple dateTimeTuple = new DateTimeTuple(unitPriceDataList.get(0).getDateTime(), unitPriceDataList.get(unitPriceDataList.size() - 1).getDateTime());
		dateTimeTuple = updateStartAndEndDatesForLookbackPeriods(scripName, outputTimeFormat, dateTimeTuple.getStartDateTime(), dateTimeTuple.getStartDateTime(),
				dateTimeTuple.getEndDateTime(), lookbackPeriod);
		dateTimeTuple = updateStartAndEndDatesForWeekendsAndHolidays(scripName, outputTimeFormat, dateTimeTuple.getStartDateTime(), dateTimeTuple.getEndDateTime());

		if (unitPriceDataList.get(0).getTimeFormat().equals(outputTimeFormat)) {

			List<UnitPriceData> lookbackPeriodUnitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName, dateTimeTuple.getStartDateTime(),
					unitPriceDataList.get(0).getDateTime(), outputTimeFormat);

			List<UnitPriceData> lookForwardPeriodUnitPriceDataList = historicalDataReader.getUnitPriceDataForRange(scripName,
					unitPriceDataList.get(unitPriceDataList.size() - 1).getDateTime(), dateTimeTuple.getEndDateTime(), outputTimeFormat);

			if (!lookbackPeriodUnitPriceDataList.isEmpty() && lookbackPeriodUnitPriceDataList.get(lookbackPeriodUnitPriceDataList.size() - 1).equals(unitPriceDataList.get(0))) {
				lookbackPeriodUnitPriceDataList.remove(lookbackPeriodUnitPriceDataList.size() - 1);
			}

			if (!lookForwardPeriodUnitPriceDataList.isEmpty() && lookForwardPeriodUnitPriceDataList.get(0).equals(unitPriceDataList.get(unitPriceDataList.size() - 1))) {
				lookForwardPeriodUnitPriceDataList.remove(0);
			}

			List<UnitPriceData> unitPriceDataListWithLookbackPeriod = new ArrayList<UnitPriceData>();
			unitPriceDataListWithLookbackPeriod.addAll(lookbackPeriodUnitPriceDataList);
			unitPriceDataListWithLookbackPeriod.addAll(unitPriceDataList);
			unitPriceDataListWithLookbackPeriod.addAll(lookForwardPeriodUnitPriceDataList);

			return unitPriceDataListWithLookbackPeriod;

		} else {
			List<UnitPriceData> unitPriceDataListOutputTimeFormat = historicalDataReader.getUnitPriceDataForRange(scripName, dateTimeTuple.getStartDateTime(),
					dateTimeTuple.getEndDateTime(), outputTimeFormat);

			return unitPriceDataListOutputTimeFormat;
		}
	}

	private DateTimeTuple updateStartAndEndDatesForLookbackPeriods(String scripName, TimeFormat trendTimeFormat, LocalDateTime originalStartDateTime, LocalDateTime startDateTime,
			LocalDateTime endDateTime, int lookbackPeriod) {
		if (trendTimeFormat.equals(TimeFormat._1MIN) || trendTimeFormat.equals(TimeFormat._5MIN) || trendTimeFormat.equals(TimeFormat._15MIN)) {
			startDateTime = startDateTime.minusMinutes(lookbackPeriod * trendTimeFormat.getInterval());
			endDateTime = endDateTime.plusMinutes(1 * trendTimeFormat.getInterval());
		} else if (trendTimeFormat.equals(TimeFormat._1HOUR)) {
			startDateTime = startDateTime.minusHours(lookbackPeriod);
			endDateTime = endDateTime.plusHours(1);
		} else if (trendTimeFormat.equals(TimeFormat._1DAY)) {
			startDateTime = startDateTime.minusDays(lookbackPeriod);
			endDateTime = endDateTime.plusDays(1);
		} else if (trendTimeFormat.equals(TimeFormat._1WEEK)) {
			startDateTime = startDateTime.minusWeeks(lookbackPeriod);
			endDateTime = endDateTime.plusWeeks(1);
		} else if (trendTimeFormat.equals(TimeFormat._1MONTH)) {
			startDateTime = startDateTime.minusMonths(lookbackPeriod).withDayOfMonth(1);
			endDateTime = endDateTime.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
		} else {
			throw new RuntimeException("Unable to determine timeFormat " + trendTimeFormat);
		}

		// This is to check boundary conditions of historical data
		if (startDateTime.isBefore(HistoricalDataDateRange.getStartDateTime(scripName, trendTimeFormat))) {
			startDateTime = HistoricalDataDateRange.getStartDateTime(scripName, trendTimeFormat);
		}
		if (endDateTime.isAfter(HistoricalDataDateRange.getEndDateTime(scripName, trendTimeFormat))) {
			endDateTime = HistoricalDataDateRange.getEndDateTime(scripName, trendTimeFormat);
		}

		// Need to make a recursive call to take care of a scenario where the original start Date is a Monday, 3 days back goes to Friday.
		// Then we cannot get a trend for it, because trend needs 3 "business" days backward to calculate the trend. In this case, we were getting only
		// one "business" day prior to Monday which was Friday.
		while (historicalDataReader.getUnitPriceDataForRange(scripName, startDateTime, originalStartDateTime, trendTimeFormat).size() < lookbackPeriod) {
			DateTimeTuple dateTimeTuple = updateStartAndEndDatesForLookbackPeriods(scripName, trendTimeFormat, originalStartDateTime, startDateTime, endDateTime, lookbackPeriod);
			startDateTime = dateTimeTuple.getStartDateTime();
		}
		return new DateTimeTuple(startDateTime, endDateTime);
	}

	private DateTimeTuple updateStartAndEndDatesForWeekendsAndHolidays(String scripName, TimeFormat trendTimeFormat, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		// Calculating for Weekends and Holidays
		if (DayOfWeek.SATURDAY.equals(startDateTime.getDayOfWeek())) {
			startDateTime = startDateTime.minusDays(1);
		} else if (DayOfWeek.SUNDAY.equals(startDateTime.getDayOfWeek())) {
			startDateTime = startDateTime.minusDays(2);
		}

		if (DayOfWeek.SATURDAY.equals(endDateTime.getDayOfWeek())) {
			endDateTime = endDateTime.plusDays(2);
		} else if (DayOfWeek.SUNDAY.equals(endDateTime.getDayOfWeek())) {
			endDateTime = endDateTime.plusDays(1);
		}

		// This is to check boundary conditions of historical data
		if (startDateTime.isBefore(HistoricalDataDateRange.getStartDateTime(scripName, trendTimeFormat))) {
			startDateTime = HistoricalDataDateRange.getStartDateTime(scripName, trendTimeFormat);
		}
		if (endDateTime.isAfter(HistoricalDataDateRange.getEndDateTime(scripName, trendTimeFormat))) {
			endDateTime = HistoricalDataDateRange.getEndDateTime(scripName, trendTimeFormat);
		}

		// This is to take care of the holidays, if there is no entry for the currentDate (size = 0), then move a day back.
		while (CollectionUtils.isEmpty(historicalDataReader.getUnitPriceDataForRange(scripName, startDateTime, startDateTime, TimeFormat._1DAY))) {
			startDateTime = startDateTime.minusDays(1);
		}
		while (CollectionUtils.isEmpty(historicalDataReader.getUnitPriceDataForRange(scripName, endDateTime, endDateTime, TimeFormat._1DAY))) {
			endDateTime = endDateTime.plusDays(1);
		}

		return new DateTimeTuple(startDateTime, endDateTime);
	}
}
