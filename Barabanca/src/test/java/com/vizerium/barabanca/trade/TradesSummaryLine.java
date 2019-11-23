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

package com.vizerium.barabanca.trade;

import java.util.Comparator;

public class TradesSummaryLine {

	private String summaryLine;

	private float totalPayoff = 0.0f;

	private float averagePayoff = 0.0f;

	private float payoffAfterBrokerageAndTaxes = 0.0f;

	TradesSummaryLine(String summaryLine) {
		this.summaryLine = summaryLine;
		String[] summaryLineDetails = summaryLine.split(",");
		totalPayoff = Float.parseFloat(summaryLineDetails[13]);
		averagePayoff = Float.parseFloat(summaryLineDetails[14]);
		payoffAfterBrokerageAndTaxes = Float.parseFloat(summaryLineDetails[15]);
	}

	public float getTotalPayoff() {
		return totalPayoff;
	}

	public float getAveragePayoff() {
		return averagePayoff;
	}

	public float getPayoffAfterBrokerageAndTaxes() {
		return payoffAfterBrokerageAndTaxes;
	}

	@Override
	public String toString() {
		return summaryLine;
	}

	static class TradesSummaryHighestTotalPayoffComparator implements Comparator<TradesSummaryLine> {
		@Override
		public int compare(TradesSummaryLine o1, TradesSummaryLine o2) {
			return Comparator.comparing(TradesSummaryLine::getTotalPayoff).compare(o2, o1);
		}
	}

	static class TradesSummaryHighestAveragePayoffComparator implements Comparator<TradesSummaryLine> {
		@Override
		public int compare(TradesSummaryLine o1, TradesSummaryLine o2) {
			return Comparator.comparing(TradesSummaryLine::getAveragePayoff).compare(o2, o1);
		}
	}

	static class TradesSummaryHighestPayoffAfterBrokerageAndTaxesComparator implements Comparator<TradesSummaryLine> {
		@Override
		public int compare(TradesSummaryLine o1, TradesSummaryLine o2) {
			return Comparator.comparing(TradesSummaryLine::getPayoffAfterBrokerageAndTaxes).compare(o2, o1);
		}
	}
}
