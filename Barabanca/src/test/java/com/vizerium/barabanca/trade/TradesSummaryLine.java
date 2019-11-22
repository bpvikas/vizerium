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

	private String resultLine;

	private float totalPayoff = 0.0f;

	private float averagePayoff = 0.0f;

	TradesSummaryLine(String resultLine) {
		this.resultLine = resultLine;
		String[] resultLineDetails = resultLine.split(",");
		totalPayoff = Float.parseFloat(resultLineDetails[13]);
		averagePayoff = Float.parseFloat(resultLineDetails[14]);
	}

	public float getTotalPayoff() {
		return totalPayoff;
	}

	public float getAveragePayoff() {
		return averagePayoff;
	}

	@Override
	public String toString() {
		return resultLine;
	}

	static class HighestTradesSummaryLineTotalComparator implements Comparator<TradesSummaryLine> {
		@Override
		public int compare(TradesSummaryLine o1, TradesSummaryLine o2) {
			return Comparator.comparing(TradesSummaryLine::getTotalPayoff).compare(o2, o1);
		}
	}

	static class HighestTradesSummaryLineAverageComparator implements Comparator<TradesSummaryLine> {
		@Override
		public int compare(TradesSummaryLine o1, TradesSummaryLine o2) {
			return Comparator.comparing(TradesSummaryLine::getAveragePayoff).compare(o2, o1);
		}
	}

}
