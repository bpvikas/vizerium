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

import java.time.LocalDate;
import java.time.LocalTime;

public enum StartTimingPattern {

	// @formatter:off
	START901(new LocalTime[] { LocalTime.of(9, 1), LocalTime.of(9, 2), LocalTime.of(9, 3), LocalTime.of(9, 4) }, 0),
	PREOPEN907START915(new LocalTime[] { LocalTime.of(9, 7), LocalTime.of(9, 15), LocalTime.of(9, 16), LocalTime.of(9, 17) }, 1),
	PREOPEN908START916(new LocalTime[] { LocalTime.of(9, 8), LocalTime.of(9, 16), LocalTime.of(9, 17), LocalTime.of(9, 18) }, 1),
	PREOPEN909START916(new LocalTime[] { LocalTime.of(9, 9), LocalTime.of(9, 16), LocalTime.of(9, 17), LocalTime.of(9, 18) }, 1),
	START916(new LocalTime[] { LocalTime.of(9, 16), LocalTime.of(9, 17), LocalTime.of(9, 18), LocalTime.of(9, 19) }, 0),
	START955(new LocalTime[] { LocalTime.of(9, 55), LocalTime.of(9, 56), LocalTime.of(9, 57), LocalTime.of(9, 58) }, 0),
	START956(new LocalTime[] { LocalTime.of(9, 56), LocalTime.of(9, 57), LocalTime.of(9, 58), LocalTime.of(9, 59) }, 0),
	START1101(new LocalTime[] { LocalTime.of(11, 1), LocalTime.of(11, 2), LocalTime.of(11, 3), LocalTime.of(11, 4) }, 0),
	PREOPEN1108START1116(new LocalTime[] { LocalTime.of(11, 8), LocalTime.of(11, 16), LocalTime.of(11, 17), LocalTime.of(11, 18) }, 1),
	MUHURATTRADINGPREOPEN1723START1731(new LocalTime[] { LocalTime.of(17, 23), LocalTime.of(17, 31), LocalTime.of(17, 32), LocalTime.of(17, 33) }, 1),
	MUHURATTRADINGPREOPEN1823START1831(new LocalTime[] { LocalTime.of(18, 23), LocalTime.of(18, 31), LocalTime.of(18, 32), LocalTime.of(18, 33) }, 1),
	MUHURATTRADINGPREOPEN1808START1816(new LocalTime[] { LocalTime.of(18, 8), LocalTime.of(18, 16), LocalTime.of(18, 17), LocalTime.of(18, 18) }, 1);
	// @formatter:on

	private LocalTime[] startTimings;

	private int tradingStartTimePosition;

	private StartTimingPattern() {

	}

	private StartTimingPattern(LocalTime[] startTimings, int tradingStartTimePosition) {
		this.startTimings = startTimings;
		this.tradingStartTimePosition = tradingStartTimePosition;
	}

	public static StartTimingPattern isValid(LocalDate date, LocalTime[] startTimingsToBeValidated) {

		if (startTimingsToBeValidated == null || startTimingsToBeValidated.length != 4) {
			throw new RuntimeException("Start Timings for the day " + date + " are not among the valid sets." + startTimingsToBeValidated);
		}

		for (StartTimingPattern existingValues : values()) {
			if (existingValues.startTimings[0].equals(startTimingsToBeValidated[0]) && existingValues.startTimings[1].equals(startTimingsToBeValidated[1])
					&& existingValues.startTimings[2].equals(startTimingsToBeValidated[2]) && existingValues.startTimings[3].equals(startTimingsToBeValidated[3])) {
				return existingValues;
			}
		}
		throw new RuntimeException("Start Timings for the day " + date + " are not among the valid sets." + startTimingsToBeValidated);
	}

	public int getTradingStartTimePosition() {
		return tradingStartTimePosition;
	}

	public LocalTime getTradingStartTime() {
		return startTimings[tradingStartTimePosition];
	}

	public String toString() {
		return startTimings[0].toString() + "," + startTimings[1].toString() + "," + startTimings[2].toString() + "," + startTimings[3].toString();
	}
}
