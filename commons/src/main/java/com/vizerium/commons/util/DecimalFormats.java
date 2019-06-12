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

import java.math.RoundingMode;
import java.text.DecimalFormat;

/*
 * The purpose of creating this file is that DecimalFormat.init() is a very heavy operation and slows down the overall speed of the application 
 * by approximately 4 times. This is being heavily called in OptionStrategiesWithPayoff and having pre-initialised static values will stop the 
 * DecimalFormat.init() method from being called multiple times.
 * 
 * 
 * Math.round
 * 2019-06-12 17:59:46 INFO  Positional:54 - Output - Analysed 24957655 in 118 seconds, after eliminating 7510775 same option with opposite actions.
 * 
 * DecimalFormat.format
 * 2019-06-12 15:52:36 INFO  Positional:54 - Output - Analysed 24957655 in 131 seconds, after eliminating 7510775 same option with opposite actions.
 * 
 * StringBuffer Manipulation
 * 2019-06-12 14:45:24 INFO  Positional:54 - Output - Analysed 24957655 in 141 seconds, after eliminating 7510775 same option with opposite actions.
 * 
 * Without all this nonsense
 * 2019-06-12 22:20:05 INFO  Positional:54 - Output - Analysed 24957655 in 62 seconds, after eliminating 7510775 same option with opposite actions.
 * 
 */

public class DecimalFormats {

	private static DecimalFormat sdf;
	static {
		sdf = new DecimalFormat("0.0");
		sdf.setRoundingMode(RoundingMode.UP);
	}

	private static DecimalFormat ddf;
	static {
		ddf = new DecimalFormat("0.00");
		ddf.setRoundingMode(RoundingMode.UP);
	}

	public static float roundUpTo1Decimal_Method0(float d) {
		return Math.round(d * 10.0f) / 10.0f;
	}

	public static float roundUpTo2Decimals_Method0(float d) {
		return Math.round(d * 100.0f) / 100.0f;
	}

	/*
	 * The code below is to round to 1 decimal place with RoundingMode.UP
	 * There is a bug in RoundingMode.UP +ve numbers less than 0.01 round off to 0 instead of 0.1 even if RoundingMode.UP is used.
	 * Using the other RoundingModes like CEILING, FLOOR, etc, did not help either. 
	 */
	public static float roundUpTo1Decimal_Method1(float d) {
		if (d > 0.0 && d < 0.01) {
			return 0.1f;
		} else {
			return Float.parseFloat(sdf.format(d));
		}
	}

	/*
	 * The code below is to round to 2 decimal places with RoundingMode.UP
	 * There is a bug in RoundingMode.UP +ve numbers less than 0.001 round off to 0 instead of 0.01 even if RoundingMode.UP is used.
	 * Using the other RoundingModes like CEILING, FLOOR, etc, did not help either. 
	 */
	public static float roundUpTo2Decimals_Method1(float d) {
		if (d > 0.0 && d < 0.001) {
			return 0.01f;
		} else {
			return Float.parseFloat(ddf.format(d));
		}
	}

	public static float roundUpTo1Decimal_Method2(float d) {
		StringBuilder builder = new StringBuilder();
		if (d < 0) {
			builder.append('-');
			d = -d;
		}
		if (d * 1e1 + 0.999999999 > Long.MAX_VALUE) {
			// TODO write a fall back.
			throw new IllegalArgumentException("number too large");
		}
		long scaled = (long) (d * 1e1 + 0.999999999);
		long factor = 10;
		int scale = 2;
		long scaled2 = scaled / 10;
		while (factor <= scaled2) {
			factor *= 10;
			scale++;
		}
		while (scale > 0) {
			if (scale == 1)
				builder.append('.');
			long c = scaled / factor % 10;
			factor /= 10;
			builder.append((char) ('0' + c));
			scale--;
		}
		return Float.parseFloat(builder.toString());
	}

	public static float roundUpTo2Decimals_Method2(float d) {
		StringBuilder builder = new StringBuilder();
		if (d < 0) {
			builder.append('-');
			d = -d;
		}
		if (d * 1e2 + 0.999999999 > Long.MAX_VALUE) {
			// TODO write a fall back.
			throw new IllegalArgumentException("number too large");
		}
		long scaled = (long) (d * 1e2 + 0.999999999);
		long factor = 100;
		int scale = 3;
		long scaled2 = scaled / 10;
		while (factor <= scaled2) {
			factor *= 10;
			scale++;
		}
		while (scale > 0) {
			if (scale == 2)
				builder.append('.');
			long c = scaled / factor % 10;
			factor /= 10;
			builder.append((char) ('0' + c));
			scale--;
		}
		return Float.parseFloat(builder.toString());
	}

	// The original code is picked from
	// https://stackoverflow.com/questions/8553672/a-faster-alternative-to-decimalformat-format
	// which deals with the problem that the DecimalFormat.format is a very heavy operation
	// The code below is to round to 6 decimal places with RoundingMode.HALF_UP
	public static void roundHalfUpTo6Decimals_Method2(StringBuilder builder, double d) {
		if (d < 0) {
			builder.append('-');
			d = -d;
		}
		if (d * 1e6 + 0.5 > Long.MAX_VALUE) {
			// TODO write a fall back.
			throw new IllegalArgumentException("number too large");
		}
		long scaled = (long) (d * 1e6 + 0.5);
		long factor = 1000000;
		int scale = 7;
		long scaled2 = scaled / 10;
		while (factor <= scaled2) {
			factor *= 10;
			scale++;
		}
		while (scale > 0) {
			if (scale == 6)
				builder.append('.');
			long c = scaled / factor % 10;
			factor /= 10;
			builder.append((char) ('0' + c));
			scale--;
		}
	}

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		long start = System.nanoTime();
		final int runs = 20000000;
		for (int i = 0; i < runs; i++) {
			roundHalfUpTo6Decimals_Method2(sb, i * 1e-6);
			sb.setLength(0);
		}
		long time = System.nanoTime() - start;
		System.out.printf("Took %,d ns per append double%n", time / runs);
	}
}
