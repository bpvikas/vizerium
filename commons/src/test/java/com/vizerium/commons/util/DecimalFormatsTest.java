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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DecimalFormatsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetForSingleDecimal() {
		float f0 = 0.0f;
		float f1 = 0.0000003f;
		float f2 = 0.009f;
		float f3 = 0.0099f;
		float f4 = 0.01f;
		float f4a = 0.0011f;
		float f5 = 0.02f;
		float f6 = 0.05f;
		float f7 = 0.08f;
		float f8 = 0.42f;
		float f9 = 0.81f;

		System.out.println("f0 -> " + f0 + " -> " + DecimalFormats.roundUpTo1Decimal_Method0(f0) + " -> " + DecimalFormats.roundUpTo1Decimal_Method1(f0) + " -> "
				+ DecimalFormats.roundUpTo1Decimal_Method2(f0));
		System.out.println("f1 -> " + f1 + " -> " + DecimalFormats.roundUpTo1Decimal_Method0(f1) + " -> " + DecimalFormats.roundUpTo1Decimal_Method1(f1) + " -> "
				+ DecimalFormats.roundUpTo1Decimal_Method2(f1));
		System.out.println("f2 -> " + f2 + " -> " + DecimalFormats.roundUpTo1Decimal_Method0(f2) + " -> " + DecimalFormats.roundUpTo1Decimal_Method1(f2) + " -> "
				+ DecimalFormats.roundUpTo1Decimal_Method2(f2));
		System.out.println("f3 -> " + f3 + " -> " + DecimalFormats.roundUpTo1Decimal_Method0(f3) + " -> " + DecimalFormats.roundUpTo1Decimal_Method1(f3) + " -> "
				+ DecimalFormats.roundUpTo1Decimal_Method2(f3));
		System.out.println("f4 -> " + f4 + " -> " + DecimalFormats.roundUpTo1Decimal_Method0(f4) + " -> " + DecimalFormats.roundUpTo1Decimal_Method1(f4) + " -> "
				+ DecimalFormats.roundUpTo1Decimal_Method2(f4));
		System.out.println("f4a -> " + f4a + " -> " + DecimalFormats.roundUpTo1Decimal_Method0(f4a) + " -> " + DecimalFormats.roundUpTo1Decimal_Method1(f4a) + " -> "
				+ DecimalFormats.roundUpTo1Decimal_Method2(f4a));
		System.out.println("f5 -> " + f5 + " -> " + DecimalFormats.roundUpTo1Decimal_Method0(f5) + " -> " + DecimalFormats.roundUpTo1Decimal_Method1(f5) + " -> "
				+ DecimalFormats.roundUpTo1Decimal_Method2(f5));
		System.out.println("f6 -> " + f6 + " -> " + DecimalFormats.roundUpTo1Decimal_Method0(f6) + " -> " + DecimalFormats.roundUpTo1Decimal_Method1(f6) + " -> "
				+ DecimalFormats.roundUpTo1Decimal_Method2(f6));
		System.out.println("f7 -> " + f7 + " -> " + DecimalFormats.roundUpTo1Decimal_Method0(f7) + " -> " + DecimalFormats.roundUpTo1Decimal_Method1(f7) + " -> "
				+ DecimalFormats.roundUpTo1Decimal_Method2(f7));
		System.out.println("f8 -> " + f8 + " -> " + DecimalFormats.roundUpTo1Decimal_Method0(f8) + " -> " + DecimalFormats.roundUpTo1Decimal_Method1(f8) + " -> "
				+ DecimalFormats.roundUpTo1Decimal_Method2(f8));
		System.out.println("f9 -> " + f9 + " -> " + DecimalFormats.roundUpTo1Decimal_Method0(f9) + " -> " + DecimalFormats.roundUpTo1Decimal_Method1(f9) + " -> "
				+ DecimalFormats.roundUpTo1Decimal_Method2(f9));

		Assert.assertEquals("f0", DecimalFormats.roundUpTo1Decimal_Method1(f0), DecimalFormats.roundUpTo1Decimal_Method0(f0), 0.0f);
		Assert.assertEquals("f1", DecimalFormats.roundUpTo1Decimal_Method1(f1), DecimalFormats.roundUpTo1Decimal_Method0(f1), 0.0f);
		Assert.assertEquals("f2", DecimalFormats.roundUpTo1Decimal_Method1(f2), DecimalFormats.roundUpTo1Decimal_Method0(f2), 0.0f);
		Assert.assertEquals("f3", DecimalFormats.roundUpTo1Decimal_Method1(f3), DecimalFormats.roundUpTo1Decimal_Method0(f3), 0.0f);
		Assert.assertEquals("f4", DecimalFormats.roundUpTo1Decimal_Method1(f4), DecimalFormats.roundUpTo1Decimal_Method0(f4), 0.0f);
		Assert.assertEquals("f4a", DecimalFormats.roundUpTo1Decimal_Method1(f4a), DecimalFormats.roundUpTo1Decimal_Method0(f4a), 0.0f);
		Assert.assertEquals("f5", DecimalFormats.roundUpTo1Decimal_Method1(f5), DecimalFormats.roundUpTo1Decimal_Method0(f5), 0.0f);
		Assert.assertEquals("f6", DecimalFormats.roundUpTo1Decimal_Method1(f6), DecimalFormats.roundUpTo1Decimal_Method0(f6), 0.0f);
		Assert.assertEquals("f7", DecimalFormats.roundUpTo1Decimal_Method1(f7), DecimalFormats.roundUpTo1Decimal_Method0(f7), 0.0f);
		Assert.assertEquals("f8", DecimalFormats.roundUpTo1Decimal_Method1(f8), DecimalFormats.roundUpTo1Decimal_Method0(f8), 0.0f);
		Assert.assertEquals("f9", DecimalFormats.roundUpTo1Decimal_Method1(f9), DecimalFormats.roundUpTo1Decimal_Method0(f9), 0.0f);

		Assert.assertEquals("f0", DecimalFormats.roundUpTo1Decimal_Method1(f0), DecimalFormats.roundUpTo1Decimal_Method2(f0), 0.0f);
		Assert.assertEquals("f1", DecimalFormats.roundUpTo1Decimal_Method1(f1), DecimalFormats.roundUpTo1Decimal_Method2(f1), 0.0f);
		Assert.assertEquals("f2", DecimalFormats.roundUpTo1Decimal_Method1(f2), DecimalFormats.roundUpTo1Decimal_Method2(f2), 0.0f);
		Assert.assertEquals("f3", DecimalFormats.roundUpTo1Decimal_Method1(f3), DecimalFormats.roundUpTo1Decimal_Method2(f3), 0.0f);
		Assert.assertEquals("f4", DecimalFormats.roundUpTo1Decimal_Method1(f4), DecimalFormats.roundUpTo1Decimal_Method2(f4), 0.0f);
		Assert.assertEquals("f4a", DecimalFormats.roundUpTo1Decimal_Method1(f4a), DecimalFormats.roundUpTo1Decimal_Method2(f4a), 0.0f);
		Assert.assertEquals("f5", DecimalFormats.roundUpTo1Decimal_Method1(f5), DecimalFormats.roundUpTo1Decimal_Method2(f5), 0.0f);
		Assert.assertEquals("f6", DecimalFormats.roundUpTo1Decimal_Method1(f6), DecimalFormats.roundUpTo1Decimal_Method2(f6), 0.0f);
		Assert.assertEquals("f7", DecimalFormats.roundUpTo1Decimal_Method1(f7), DecimalFormats.roundUpTo1Decimal_Method2(f7), 0.0f);
		Assert.assertEquals("f8", DecimalFormats.roundUpTo1Decimal_Method1(f8), DecimalFormats.roundUpTo1Decimal_Method2(f8), 0.0f);
		Assert.assertEquals("f9", DecimalFormats.roundUpTo1Decimal_Method1(f9), DecimalFormats.roundUpTo1Decimal_Method2(f9), 0.0f);
	}

	@Test
	public void testGetForDoubleDecimal() {
		float f0 = 0.0f;
		float f1 = 0.00000003f;
		float f2 = 0.0009f;
		float f3 = 0.00099f;
		float f4 = 0.001f;
		float f4a = 0.0011f;
		float f5 = 0.002f;
		float f6 = 0.005f;
		float f7 = 0.008f;
		float f8 = 0.042f;
		float f9 = 0.081f;

		System.out.println("f0 -> " + f0 + " -> " + DecimalFormats.roundUpTo2Decimals_Method0(f0) + " -> " + DecimalFormats.roundUpTo2Decimals_Method1(f0) + " -> "
				+ DecimalFormats.roundUpTo2Decimals_Method2(f0));
		System.out.println("f1 -> " + f1 + " -> " + DecimalFormats.roundUpTo2Decimals_Method0(f1) + " -> " + DecimalFormats.roundUpTo2Decimals_Method1(f1) + " -> "
				+ DecimalFormats.roundUpTo2Decimals_Method2(f1));
		System.out.println("f2 -> " + f2 + " -> " + DecimalFormats.roundUpTo2Decimals_Method0(f2) + " -> " + DecimalFormats.roundUpTo2Decimals_Method1(f2) + " -> "
				+ DecimalFormats.roundUpTo2Decimals_Method2(f2));
		System.out.println("f3 -> " + f3 + " -> " + DecimalFormats.roundUpTo2Decimals_Method0(f3) + " -> " + DecimalFormats.roundUpTo2Decimals_Method1(f3) + " -> "
				+ DecimalFormats.roundUpTo2Decimals_Method2(f3));
		System.out.println("f4 -> " + f4 + " -> " + DecimalFormats.roundUpTo2Decimals_Method0(f4) + " -> " + DecimalFormats.roundUpTo2Decimals_Method1(f4) + " -> "
				+ DecimalFormats.roundUpTo2Decimals_Method2(f4));
		System.out.println("f4a -> " + f4a + " -> " + DecimalFormats.roundUpTo2Decimals_Method0(f4a) + " -> " + DecimalFormats.roundUpTo2Decimals_Method1(f4a) + " -> "
				+ DecimalFormats.roundUpTo2Decimals_Method2(f4a));
		System.out.println("f5 -> " + f5 + " -> " + DecimalFormats.roundUpTo2Decimals_Method0(f5) + " -> " + DecimalFormats.roundUpTo2Decimals_Method1(f5) + " -> "
				+ DecimalFormats.roundUpTo2Decimals_Method2(f5));
		System.out.println("f6 -> " + f6 + " -> " + DecimalFormats.roundUpTo2Decimals_Method0(f6) + " -> " + DecimalFormats.roundUpTo2Decimals_Method1(f6) + " -> "
				+ DecimalFormats.roundUpTo2Decimals_Method2(f6));
		System.out.println("f7 -> " + f7 + " -> " + DecimalFormats.roundUpTo2Decimals_Method0(f7) + " -> " + DecimalFormats.roundUpTo2Decimals_Method1(f7) + " -> "
				+ DecimalFormats.roundUpTo2Decimals_Method2(f7));
		System.out.println("f8 -> " + f8 + " -> " + DecimalFormats.roundUpTo2Decimals_Method0(f8) + " -> " + DecimalFormats.roundUpTo2Decimals_Method1(f8) + " -> "
				+ DecimalFormats.roundUpTo2Decimals_Method2(f8));
		System.out.println("f9 -> " + f9 + " -> " + DecimalFormats.roundUpTo2Decimals_Method0(f9) + " -> " + DecimalFormats.roundUpTo2Decimals_Method1(f9) + " -> "
				+ DecimalFormats.roundUpTo2Decimals_Method2(f9));

		Assert.assertEquals("f0", DecimalFormats.roundUpTo2Decimals_Method1(f0), DecimalFormats.roundUpTo2Decimals_Method0(f0), 0.0f);
		Assert.assertEquals("f1", DecimalFormats.roundUpTo2Decimals_Method1(f1), DecimalFormats.roundUpTo2Decimals_Method0(f1), 0.0f);
		Assert.assertEquals("f2", DecimalFormats.roundUpTo2Decimals_Method1(f2), DecimalFormats.roundUpTo2Decimals_Method0(f2), 0.0f);
		Assert.assertEquals("f3", DecimalFormats.roundUpTo2Decimals_Method1(f3), DecimalFormats.roundUpTo2Decimals_Method0(f3), 0.0f);
		Assert.assertEquals("f4", DecimalFormats.roundUpTo2Decimals_Method1(f4), DecimalFormats.roundUpTo2Decimals_Method0(f4), 0.0f);
		Assert.assertEquals("f4a", DecimalFormats.roundUpTo2Decimals_Method1(f4a), DecimalFormats.roundUpTo2Decimals_Method0(f4a), 0.0f);
		Assert.assertEquals("f5", DecimalFormats.roundUpTo2Decimals_Method1(f5), DecimalFormats.roundUpTo2Decimals_Method0(f5), 0.0f);
		Assert.assertEquals("f6", DecimalFormats.roundUpTo2Decimals_Method1(f6), DecimalFormats.roundUpTo2Decimals_Method0(f6), 0.0f);
		Assert.assertEquals("f7", DecimalFormats.roundUpTo2Decimals_Method1(f7), DecimalFormats.roundUpTo2Decimals_Method0(f7), 0.0f);
		Assert.assertEquals("f8", DecimalFormats.roundUpTo2Decimals_Method1(f8), DecimalFormats.roundUpTo2Decimals_Method0(f8), 0.0f);
		Assert.assertEquals("f9", DecimalFormats.roundUpTo2Decimals_Method1(f9), DecimalFormats.roundUpTo2Decimals_Method0(f9), 0.0f);

		Assert.assertEquals("f0", DecimalFormats.roundUpTo2Decimals_Method1(f0), DecimalFormats.roundUpTo2Decimals_Method2(f0), 0.0f);
		Assert.assertEquals("f1", DecimalFormats.roundUpTo2Decimals_Method1(f1), DecimalFormats.roundUpTo2Decimals_Method2(f1), 0.0f);
		Assert.assertEquals("f2", DecimalFormats.roundUpTo2Decimals_Method1(f2), DecimalFormats.roundUpTo2Decimals_Method2(f2), 0.0f);
		Assert.assertEquals("f3", DecimalFormats.roundUpTo2Decimals_Method1(f3), DecimalFormats.roundUpTo2Decimals_Method2(f3), 0.0f);
		Assert.assertEquals("f4", DecimalFormats.roundUpTo2Decimals_Method1(f4), DecimalFormats.roundUpTo2Decimals_Method2(f4), 0.0f);
		Assert.assertEquals("f4a", DecimalFormats.roundUpTo2Decimals_Method1(f4a), DecimalFormats.roundUpTo2Decimals_Method2(f4a), 0.0f);
		Assert.assertEquals("f5", DecimalFormats.roundUpTo2Decimals_Method1(f5), DecimalFormats.roundUpTo2Decimals_Method2(f5), 0.0f);
		Assert.assertEquals("f6", DecimalFormats.roundUpTo2Decimals_Method1(f6), DecimalFormats.roundUpTo2Decimals_Method2(f6), 0.0f);
		Assert.assertEquals("f7", DecimalFormats.roundUpTo2Decimals_Method1(f7), DecimalFormats.roundUpTo2Decimals_Method2(f7), 0.0f);
		Assert.assertEquals("f8", DecimalFormats.roundUpTo2Decimals_Method1(f8), DecimalFormats.roundUpTo2Decimals_Method2(f8), 0.0f);
		Assert.assertEquals("f9", DecimalFormats.roundUpTo2Decimals_Method1(f9), DecimalFormats.roundUpTo2Decimals_Method2(f9), 0.0f);
	}

	@Test
	public void testRoundHalfUpTo6Decimals() {
		for (String s : "-0.000001,0.000009,-0.000010,0.100000,1.100000,10.100000".split(",")) {
			double d = Double.parseDouble(s);
			StringBuilder sb = new StringBuilder();
			DecimalFormats.roundHalfUpTo6Decimals_Method2(sb, d);
			Assert.assertEquals(s, sb.toString());
		}
	}

}
