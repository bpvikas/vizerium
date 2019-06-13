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

package com.vizerium.payoffmatrix.dao;

import com.vizerium.payoffmatrix.historical.DayPriceData;
import com.vizerium.payoffmatrix.volatility.DateRange;

public class HistoricalData {

	private DayPriceData[] dayPriceData;

	public HistoricalData(DayPriceData[] dayPriceData) {
		if (dayPriceData == null || dayPriceData.length == 0) {
			throw new RuntimeException("Day Price data not obtained.");
		}
		this.dayPriceData = dayPriceData;
	}

	public DayPriceData[] getDayPriceData() {
		return dayPriceData;
	}

	public float[] getClosingPrices() {
		float[] closingPrices = new float[dayPriceData.length];
		for (int i = 0; i < dayPriceData.length; i++) {
			closingPrices[i] = dayPriceData[i].getClose();
		}
		return closingPrices;
	}

	public DateRange getStartAndEndDates() {
		return new DateRange(dayPriceData[0].getDate(), dayPriceData[dayPriceData.length - 1].getDate());
	}

	public DayPriceData getFirst() {
		return dayPriceData[0];
	}

	public DayPriceData getLast() {
		return dayPriceData[dayPriceData.length - 1];
	}

	public float getLastClosingPrice() {
		return getLast().getClose();
	}
}
