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
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class PeriodTrends extends ArrayList<PeriodTrend> {

	private static final long serialVersionUID = 1481145456008780102L;

	private static final Logger logger = Logger.getLogger(PeriodTrends.class);

	public Trend getPriorTrend(LocalDateTime unitPriceDateTime) {
		for (int i = 1; i < size() - 1; i++) {
			if (get(i).getStartDateTime().equals(unitPriceDateTime)) {
				logger.debug("For " + unitPriceDateTime + ", " + get(i - 1));
				return get(i - 1).getTrend();
			} else if (get(i).getStartDateTime().isBefore(unitPriceDateTime) && get(i + 1).getStartDateTime().isAfter(unitPriceDateTime)) {
				logger.debug("For " + unitPriceDateTime + ", " + get(i - 1));
				return get(i - 1).getTrend();
			}
		}
		if (!unitPriceDateTime.isBefore(get(size() - 1).getStartDateTime())) {
			return get(size() - 1).getTrend();
		}
		throw new RuntimeException("Unable to determine Trend for " + unitPriceDateTime);
	}
}
