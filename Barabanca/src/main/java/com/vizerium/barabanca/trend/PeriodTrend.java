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

package com.vizerium.barabanca.trend;

import java.time.LocalDateTime;

import com.vizerium.commons.dao.TimeFormat;

public class PeriodTrend {

	private LocalDateTime startDateTime;

	private TimeFormat timeFormat;

	private Trend trend;

	public PeriodTrend() {
	}

	public PeriodTrend(LocalDateTime startDateTime, TimeFormat timeFormat, Trend trend) {
		this.startDateTime = startDateTime;
		this.timeFormat = timeFormat;
		this.trend = trend;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public TimeFormat getTimeFormat() {
		return timeFormat;
	}

	public Trend getTrend() {
		return trend;
	}

	@Override
	public String toString() {
		return trend.name() + " trend @ " + startDateTime + " on " + timeFormat.getProperty() + " chart.";
	}
}