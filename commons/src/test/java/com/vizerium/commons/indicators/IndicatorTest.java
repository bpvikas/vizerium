package com.vizerium.commons.indicators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

public class IndicatorTest {

	/*
	 * This is just a test case whether I can annotate elements and put them in a list. Tried using an annotation @interface but while trying to put it in a List or Map, could not
	 * add elements of a certain annotation.
	 */

	@Test
	public void testIndicatorsForMap() {
		Map<Indicators, Indicator> indicators = new TreeMap<Indicators, Indicator>();
		indicators.put(Indicators.MACD, new MACD());
		indicators.put(Indicators.STOCHASTIC, new Stochastic());
	}

	@Test
	public void testIndicatorsForList() {
		List<Indicator> indicators = new ArrayList<Indicator>();
		indicators.add(new MACD());
		indicators.add(new Stochastic());
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
}
