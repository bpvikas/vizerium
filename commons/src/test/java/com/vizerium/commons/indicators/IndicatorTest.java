package com.vizerium.commons.indicators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class IndicatorTest {

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
}
