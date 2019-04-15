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

import java.util.ArrayList;
import java.util.List;

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
		System.out.println("CanonicalName -> " + this.getClass().getCanonicalName());
		System.out.println("Name -> " + this.getClass().getName());
		System.out.println("SimpleName -> " + this.getClass().getSimpleName());
		System.out.println("TypeName -> " + this.getClass().getTypeName());
	}
}
