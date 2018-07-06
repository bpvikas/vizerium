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
import java.util.HashMap;
import java.util.Map;

public class TEI implements Exchange {

	private static Map<String, Index> indices = new HashMap<String, Index>();

	private static final TEI tei = new TEI();

	private TEI() {
		populateIndices();
	}

	@Override
	public String getName() {
		return TEI.class.getName();
	}

	@Override
	public DayOfWeek[] getWeeklyHolidays() {
		return new DayOfWeek[] { DayOfWeek.SATURDAY, DayOfWeek.SUNDAY };
	}

	@Override
	public LocalDate[] getYearHolidays(int year) {
		return new LocalDate[0];
	}

	public static TEI getExchange() {
		return tei;
	}

	@Override
	public Index getIndex(String name) {
		return indices.get(name);
	}

	@Override
	public Index[] getIndices() {
		return indices.values().toArray(new Index[indices.size()]);
	}

	private void populateIndices() {
	}
}
