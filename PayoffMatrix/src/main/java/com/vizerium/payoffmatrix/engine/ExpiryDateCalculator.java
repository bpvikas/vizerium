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

package com.vizerium.payoffmatrix.engine;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.apache.log4j.Logger;

import com.vizerium.payoffmatrix.option.ContractDuration;
import com.vizerium.payoffmatrix.option.ContractSeries;

public class ExpiryDateCalculator {

	private static final Logger logger = Logger.getLogger(ExpiryDateCalculator.class);

	public static LocalDate convertToExpiryDate(String contractDurationProperty, String contactSeriesProperty) {
		return convertToExpiryDate(contractDurationProperty, contactSeriesProperty, LocalDate.now());
	}

	public static LocalDate convertToExpiryDate(String contractDurationProperty, String contactSeriesProperty, LocalDate localDate) {
		ContractDuration contractDuration = ContractDuration.getByProperty(contractDurationProperty);
		ContractSeries contractSeries = ContractSeries.getByProperty(contactSeriesProperty);
		return convertToExpiryDate(contractDuration, contractSeries, localDate);
	}

	public static LocalDate convertToExpiryDate(ContractDuration contractDuration, ContractSeries contractSeries) {
		return convertToExpiryDate(contractDuration, contractSeries, LocalDate.now());
	}

	public static LocalDate convertToExpiryDate(ContractDuration contractDuration, ContractSeries contractSeries, LocalDate localDate) {
		LocalDate expiryLocalDate = null;

		if (contractSeries == ContractSeries.NEAR) {
			if (contractDuration == ContractDuration.MONTH) {
				expiryLocalDate = localDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
				if (localDate.isAfter(expiryLocalDate)) {
					expiryLocalDate = expiryLocalDate.plusMonths(1).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
				}
			} else if (contractDuration == ContractDuration.WEEK) {
				expiryLocalDate = localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
			} else {
				throw new RuntimeException("Unable to determine contractPeriod, whether WEEK or MONTH.");
			}
		} else if (contractSeries == ContractSeries.MID) {
			if (contractDuration == ContractDuration.MONTH) {
				expiryLocalDate = localDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
				if (localDate.isAfter(expiryLocalDate)) {
					expiryLocalDate = expiryLocalDate.plusMonths(2).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
				} else {
					expiryLocalDate = expiryLocalDate.plusMonths(1).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
				}
			} else if (contractDuration == ContractDuration.WEEK) {
				expiryLocalDate = localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
				expiryLocalDate = expiryLocalDate.plusWeeks(1).with(DayOfWeek.THURSDAY);
			} else {
				throw new RuntimeException("Unable to determine contractPeriod, whether WEEK or MONTH.");
			}
		} else if (contractSeries == ContractSeries.FAR) {
			if (contractDuration == ContractDuration.MONTH) {
				expiryLocalDate = localDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
				if (localDate.isAfter(expiryLocalDate)) {
					expiryLocalDate = expiryLocalDate.plusMonths(3).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
				} else {
					expiryLocalDate = expiryLocalDate.plusMonths(2).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
				}
			} else if (contractDuration == ContractDuration.WEEK) {
				expiryLocalDate = localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
				expiryLocalDate = expiryLocalDate.plusWeeks(2).with(DayOfWeek.THURSDAY);
			} else {
				throw new RuntimeException("Unable to determine contractPeriod, whether WEEK or MONTH.");
			}
		} else {
			throw new RuntimeException("Unable to determine contractPeriodDuration, whether NEAR, MID or FAR.");
		}
		logger.info(localDate + " " + contractSeries.name() + " " + contractDuration.name() + " " + expiryLocalDate);
		return expiryLocalDate;
	}
}
