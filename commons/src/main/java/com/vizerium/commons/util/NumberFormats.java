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

package com.vizerium.commons.util;

import java.text.NumberFormat;

public class NumberFormats {

	private static NumberFormat pnf = NumberFormat.getInstance();
	static {
		pnf.setMaximumFractionDigits(2);
		pnf.setMinimumFractionDigits(2);
		pnf.setGroupingUsed(false);
	}

	private static NumberFormat manf = NumberFormat.getInstance();
	static {
		manf.setMinimumFractionDigits(4);
		manf.setMaximumFractionDigits(4);
		manf.setGroupingUsed(false);
	}

	private static NumberFormat mnf = NumberFormat.getInstance();
	static {
		mnf.setMinimumIntegerDigits(2);
		mnf.setMaximumIntegerDigits(2);
	}

	public static NumberFormat getForPrice() {
		return pnf;
	}

	public static NumberFormat getForMovingAverage() {
		return manf;
	}

	public static NumberFormat getForMonth() {
		return mnf;
	}
}
