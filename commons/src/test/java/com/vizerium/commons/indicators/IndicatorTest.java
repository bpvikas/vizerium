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

package com.vizerium.commons.indicators;

import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class IndicatorTest {

	/*
	 * This is just a test case whether I can annotate elements and put them in a list. Tried using an annotation @interface but while trying to put it in a List or Map, could not
	 * add elements of a certain annotation.
	 */
	@Test
	public void testIndicatorsForList() {
		List<Indicator<MACD>> indicators = new ArrayList<Indicator<MACD>>();
		indicators.add(new MACD());
	}

	@Test
	public void testString() {
		String p = "";
		updateString(p);
		Assert.assertNotEquals("Passing string to a called method does not change the value of the String in the calling method.", "hi,hi,", p);
	}

	private void updateString(String p) {
		p += "hi,";
		p += "hi,";
	}

	@Test
	public void testStringBuilder() {
		StringBuilder p = new StringBuilder();
		updateStringBuilder(p);
		Assert.assertEquals("hi,hi,", p.toString());
	}

	private void updateStringBuilder(StringBuilder p) {
		p.append("hi,");
		p.append("hi,");
	}

	@Test
	public void testClassName() {
		Assert.assertEquals("com.vizerium.commons.indicators.IndicatorTest", this.getClass().getCanonicalName());
		Assert.assertEquals("com.vizerium.commons.indicators.IndicatorTest", this.getClass().getName());
		Assert.assertEquals("IndicatorTest", this.getClass().getSimpleName());
		Assert.assertEquals("com.vizerium.commons.indicators.IndicatorTest", this.getClass().getTypeName());
	}

	@Test
	public void getFileTimes() {
		// String fileName = "C:/Users/sahil/Desktop/3.png";
		String fileName = "This PC/Q6 plus/Internal storage/DCIM/Camera/Indicator_panel_blocking.png";
		Path p = Paths.get(fileName);
		try {
			Map<String, Object> fileAttributes = Files.readAttributes(p, "*");
			System.out.println("Setting creationTime from : " + fileAttributes.get("creationTime") + " to lastModifiedTime: " + fileAttributes.get("lastModifiedTime"));
			// Files.setAttribute(p, "creationTime", fileAttributes.get("lastModifiedTime"));
		} catch (IOException e) {
			System.err.println("Cannot change the creation time. " + e);
		}
	}

	@Test
	public void testDaysBetween() {
		LocalDate fromDate = LocalDate.of(2019, 6, 9);
		LocalDate toDate = LocalDate.of(2019, 6, 13);

		System.out.println(ChronoUnit.DAYS.between(fromDate, toDate));
	}

	@Test
	public void testRounding() {
		double f1 = 0.0000003;
		double f2 = 0.009;
		double f3 = 0.0099;
		double f4 = 0.01;
		double f5 = 0.02;
		double f6 = 0.05;
		double f7 = 0.08;
		double f8 = 0.42;
		double f9 = 0.81;

		DecimalFormat df = new DecimalFormat("0.0");
		df.setRoundingMode(RoundingMode.UP);

		System.out.println(f1 + " -> " + Float.parseFloat(df.format(f1)));
		System.out.println(f2 + " -> " + Float.parseFloat(df.format(f2)));
		System.out.println(f3 + " -> " + Float.parseFloat(df.format(f3)));
		System.out.println(f4 + " -> " + Float.parseFloat(df.format(f4)));
		System.out.println(f5 + " -> " + Float.parseFloat(df.format(f5)));
		System.out.println(f6 + " -> " + Float.parseFloat(df.format(f6)));
		System.out.println(f7 + " -> " + Float.parseFloat(df.format(f7)));
		System.out.println(f8 + " -> " + Float.parseFloat(df.format(f8)));
		System.out.println(f9 + " -> " + Float.parseFloat(df.format(f9)));
	}

}
