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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
	public void testRoundingtoNearest005() {
		Assert.assertEquals(11831.60f, Math.floor(11831.63f * 20) / 20.0, 0.001);
		Assert.assertEquals(11831.65f, Math.ceil(11831.63f * 20) / 20.0, 0.001);
		Assert.assertEquals(11831.60f, Math.floor(11831.60f * 20) / 20.0, 0.001);
		Assert.assertEquals(11831.60f, Math.ceil(11831.60f * 20) / 20.0, 0.001);
		Assert.assertEquals(11831.65f, Math.floor(11831.65f * 20) / 20.0, 0.001);
		Assert.assertEquals(11831.65f, Math.ceil(11831.65f * 20) / 20.0, 0.001);
	}

	@Test
	public void testSequentialParallelStreamExecution() {
		String[] strings = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

		System.out.println("-------\nRunning sequential\n-------");
		run(Arrays.stream(strings).sequential());
		System.out.println("-------\nRunning parallel\n-------");
		run(Arrays.stream(strings).parallel());
	}

	private void run(Stream<String> stream) {
		stream.forEach(s -> {
			System.out.println(LocalTime.now() + " - value: " + s + " - thread: " + Thread.currentThread().getName());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
}
