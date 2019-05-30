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

package com.vizerium.payoffmatrix.io;

import java.util.TreeSet;

import com.vizerium.payoffmatrix.comparator.BestRiskRewardRatioPayoffMatrixComparator;
import com.vizerium.payoffmatrix.comparator.HighestAveragePayoffMatrixComparator;
import com.vizerium.payoffmatrix.comparator.HighestProfitProbabilityPayoffMatrixComparator;
import com.vizerium.payoffmatrix.comparator.MaximumProfitPayoffMatrixComparator;
import com.vizerium.payoffmatrix.comparator.MinimumLossPayoffMatrixComparator;
import com.vizerium.payoffmatrix.engine.OptionStrategiesWithPayoff;
import com.vizerium.payoffmatrix.reports.PayoffReportXlsx;
import com.vizerium.payoffmatrix.volatility.Range;

public class Output {

	public static int analysisPayoffsLengths = 7;

	private OptionStrategiesWithPayoff existingPositionPayoff;

	private TreeSet<OptionStrategiesWithPayoff> highestProfitProbabilityPayoffs = new TreeSet<OptionStrategiesWithPayoff>(new HighestProfitProbabilityPayoffMatrixComparator());

	private TreeSet<OptionStrategiesWithPayoff> maxPositivePayoffs = new TreeSet<OptionStrategiesWithPayoff>(new MaximumProfitPayoffMatrixComparator());

	private TreeSet<OptionStrategiesWithPayoff> minNegativePayoffs = new TreeSet<OptionStrategiesWithPayoff>(new MinimumLossPayoffMatrixComparator());

	private TreeSet<OptionStrategiesWithPayoff> bestRiskRewardRatioPayoffs = new TreeSet<OptionStrategiesWithPayoff>(new BestRiskRewardRatioPayoffMatrixComparator());

	private TreeSet<OptionStrategiesWithPayoff> highestAveragePayoffs = new TreeSet<OptionStrategiesWithPayoff>(new HighestAveragePayoffMatrixComparator());

	private String underlyingName;

	private Range underlyingRange;

	private int optionStrategiesCount;

	public Output() {

	}

	public void performPayoffAnalysis(OptionStrategiesWithPayoff optionStrategiesWithPayoff) {
		if (optionStrategiesWithPayoff.isExistingOpenPosition()) {
			existingPositionPayoff = optionStrategiesWithPayoff;
		}
		compareToExistingPayoffs(highestAveragePayoffs, optionStrategiesWithPayoff);
		if (optionStrategiesWithPayoff.getProfitProbability() < 0.9f) {
			compareToExistingPayoffs(highestProfitProbabilityPayoffs, optionStrategiesWithPayoff);
		}
		compareToExistingPayoffs(maxPositivePayoffs, optionStrategiesWithPayoff);
		if (optionStrategiesWithPayoff.getNegativePayoffSum() < -2.0f) {
			compareToExistingPayoffs(minNegativePayoffs, optionStrategiesWithPayoff);
		}
		if (optionStrategiesWithPayoff.getRiskRewardRatio() > 0.1f) {
			compareToExistingPayoffs(bestRiskRewardRatioPayoffs, optionStrategiesWithPayoff);
		}
	}

	private void compareToExistingPayoffs(TreeSet<OptionStrategiesWithPayoff> payoffs, OptionStrategiesWithPayoff optionStrategiesWithPayoff) {
		payoffs.add(optionStrategiesWithPayoff);
		if (payoffs.size() > analysisPayoffsLengths) {
			payoffs.pollLast(); // pollLast retrieves and removes the last entry in the TreeSet.
		}
	}

	public String getUnderlyingName() {
		return underlyingName;
	}

	public void setUnderlyingName(String underlyingName) {
		this.underlyingName = underlyingName;
	}

	public Range getUnderlyingRange() {
		return underlyingRange;
	}

	public void setUnderlyingRange(Range underlyingRange) {
		this.underlyingRange = underlyingRange;
	}

	public int getOptionStrategiesCount() {
		return optionStrategiesCount;
	}

	public void setOptionStrategiesCount(int optionStrategiesCount) {
		this.optionStrategiesCount = optionStrategiesCount;
	}

	@Override
	public String toString() {
		return "Output " + System.lineSeparator() + printExistingPositionPayoff() + printHighestProfitProbabilityPayoffs() + printMaxPositivePayoffs() + printMinNegativePayoffs()
				+ printBestRiskRewardRatioPayoffs() + printHighestAveragePayoffs();
	}

	private String printExistingPositionPayoff() {
		if (existingPositionPayoff == null) {
			return System.lineSeparator();
		} else {
			TreeSet<OptionStrategiesWithPayoff> existingPositionPayoffSet = new TreeSet<OptionStrategiesWithPayoff>(new HighestAveragePayoffMatrixComparator());
			existingPositionPayoffSet.add(existingPositionPayoff);
			return printPayoffs("existingPositionPayoff", existingPositionPayoffSet);
		}
	}

	private String printHighestProfitProbabilityPayoffs() {
		return printPayoffs("highestProfitProbabilityPayoffs", highestProfitProbabilityPayoffs);
	}

	private String printMaxPositivePayoffs() {
		return printPayoffs("maxPositivePayoffs", maxPositivePayoffs);
	}

	private String printMinNegativePayoffs() {
		return printPayoffs("minNegativePayoffs", minNegativePayoffs);
	}

	private String printBestRiskRewardRatioPayoffs() {
		return printPayoffs("bestRiskRewardRatioPayoffs", bestRiskRewardRatioPayoffs);
	}

	private String printHighestAveragePayoffs() {
		return printPayoffs("highestAveragePayoffs", highestAveragePayoffs);
	}

	private String printPayoffs(String payoffName, TreeSet<OptionStrategiesWithPayoff> payoffs) {
		String printPayoff = System.lineSeparator() + "***************************************" + System.lineSeparator() + underlyingName + " " + payoffName + " : "
				+ System.lineSeparator() + "***************************************" + System.lineSeparator();
		if (payoffs != null && payoffs.size() > 0) {
			for (OptionStrategiesWithPayoff payoff : payoffs) {
				printPayoff += payoff;
				printPayoff += System.lineSeparator();
			}
			PayoffReportXlsx.createReport(underlyingName, payoffName, payoffs, optionStrategiesCount, underlyingRange);
		}
		return printPayoff;
	}

	public void finale() {
		// This method does nothing. I just wanted to put a breakpoint at a place where I can see all final results together.
		int a = 5;
	}
}
