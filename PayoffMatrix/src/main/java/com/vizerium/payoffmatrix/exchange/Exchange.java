/*
 * Copyright 2018 Vizerium, Inc.
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

package com.vizerium.payoffmatrix.exchange;

import java.time.DayOfWeek;
import java.time.LocalDate;

public interface Exchange {

	public String getName();

	public DayOfWeek[] getWeeklyHolidays();

	public LocalDate[] getYearHolidays(int year);

	public default boolean isHoliday(LocalDate date) {
		LocalDate[] yearHolidays = getYearHolidays(date.getYear());
		for (LocalDate holiday : yearHolidays) {
			if (date == holiday) {
				return true;
			}
		}
		DayOfWeek[] weeklyHolidays = getWeeklyHolidays();
		for (DayOfWeek holiday : weeklyHolidays) {
			if (date.getDayOfWeek() == holiday) {
				return true;
			}
		}
		return false;
	}

	public Index getIndex(String name);

	public Index[] getIndices();

}
