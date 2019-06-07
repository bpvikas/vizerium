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

package com.vizerium.commons.blackscholes;

import org.junit.Assert;
import org.junit.Test;

public class BlackScholesTest {

	// This one is the closest to the Option Pricing calculations on Zerodha at https://zerodha.com/tools/black-scholes/
	@Test
	public void testGetCallPriceGreeks() {
		double c[] = BlackScholes.getCallPriceGreeks(11843.75, 11900, 0.0606, 0.1552, 6.0 / 365.0);
		Assert.assertEquals(73.65, c[0], 0.01); // price
		Assert.assertEquals(0.429, c[1], 0.001); // delta
		Assert.assertEquals(-8.544, c[2], 0.001); // theta
		Assert.assertEquals(0.824, c[3], 0.001); // rho
		Assert.assertEquals(0.0017, c[4], 0.0001); // gamma
		Assert.assertEquals(5.963, c[5], 0.001); // vega
	}

	@Test
	public void testGetPutPriceGreeks() {
		double p[] = BlackScholes.getPutPriceGreeks(11843.75, 11900, 0.0606, 0.1552, 6.0 / 365.0);
		Assert.assertEquals(118.06, p[0], 0.01); // price
		Assert.assertEquals(-0.571, p[1], 0.001); // delta
		Assert.assertEquals(-6.570, p[2], 0.001); // theta
		Assert.assertEquals(-1.130, p[3], 0.001); // rho
		Assert.assertEquals(0.0017, p[4], 0.0001); // gamma
		Assert.assertEquals(5.963, p[5], 0.001); // vega
	}
}
