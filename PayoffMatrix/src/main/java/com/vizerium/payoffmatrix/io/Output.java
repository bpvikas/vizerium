/*
 * Copyright 2018 Vizerium, Inc.
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

import java.util.Arrays;

import com.vizerium.payoffmatrix.comparator.BestRiskRewardRatioPayoffMatrixComparator;
import com.vizerium.payoffmatrix.comparator.HighestProfitProbabilityPayoffMatrixComparator;
import com.vizerium.payoffmatrix.comparator.MaximumProfitPayoffMatrixComparator;
import com.vizerium.payoffmatrix.comparator.MinimumLossPayoffMatrixComparator;
import com.vizerium.payoffmatrix.engine.OptionStrategiesWithPayoff;
import com.vizerium.payoffmatrix.reports.PayoffReportXlsx;

public class Output {

	public static int analysisPayoffsLengths = 7;

	private OptionStrategiesWithPayoff[] optionStrategiesWithPayoffs;

	private OptionStrategiesWithPayoff existingPositionPayoff;

	private OptionStrategiesWithPayoff[] highestProfitProbabilityPayoffs;

	private OptionStrategiesWithPayoff[] maxPositivePayoffs;

	private OptionStrategiesWithPayoff[] minNegativePayoffs;

	private OptionStrategiesWithPayoff[] bestRiskRewardRatioPayoffs;

	public Output() {

	}

	public Output(OptionStrategiesWithPayoff[] optionStrategiesWithPayoffs) {
		super();
		this.optionStrategiesWithPayoffs = optionStrategiesWithPayoffs;
		performPayoffAnalysis();
	}

	public OptionStrategiesWithPayoff[] getOptionsWithPayoffs() {
		return optionStrategiesWithPayoffs;
	}

	public void setOptionsWithPayoffs(OptionStrategiesWithPayoff[] optionStrategiesWithPayoffs) {
		this.optionStrategiesWithPayoffs = optionStrategiesWithPayoffs;
		performPayoffAnalysis();
	}

	public void performPayoffAnalysis() {
		for (OptionStrategiesWithPayoff optionStrategiesWithPayoff : optionStrategiesWithPayoffs) {
			if (optionStrategiesWithPayoff.isExistingOpenPosition()) {
				existingPositionPayoff = optionStrategiesWithPayoff;
				break;
			}
		}

		if (analysisPayoffsLengths > optionStrategiesWithPayoffs.length) {
			analysisPayoffsLengths = optionStrategiesWithPayoffs.length;
		}

		highestProfitProbabilityPayoffs = new OptionStrategiesWithPayoff[analysisPayoffsLengths];
		maxPositivePayoffs = new OptionStrategiesWithPayoff[analysisPayoffsLengths];
		minNegativePayoffs = new OptionStrategiesWithPayoff[analysisPayoffsLengths];
		bestRiskRewardRatioPayoffs = new OptionStrategiesWithPayoff[analysisPayoffsLengths];

		Arrays.sort(optionStrategiesWithPayoffs, new HighestProfitProbabilityPayoffMatrixComparator());
		System.arraycopy(optionStrategiesWithPayoffs, 0, highestProfitProbabilityPayoffs, 0, highestProfitProbabilityPayoffs.length);
		Arrays.sort(optionStrategiesWithPayoffs, new MaximumProfitPayoffMatrixComparator());
		System.arraycopy(optionStrategiesWithPayoffs, 0, maxPositivePayoffs, 0, maxPositivePayoffs.length);
		Arrays.sort(optionStrategiesWithPayoffs, new MinimumLossPayoffMatrixComparator());
		System.arraycopy(optionStrategiesWithPayoffs, 0, minNegativePayoffs, 0, minNegativePayoffs.length);
		Arrays.sort(optionStrategiesWithPayoffs, new BestRiskRewardRatioPayoffMatrixComparator());
		System.arraycopy(optionStrategiesWithPayoffs, 0, bestRiskRewardRatioPayoffs, 0, bestRiskRewardRatioPayoffs.length);
	}

	@Override
	public String toString() {
		return "Output " + System.lineSeparator() + printExistingPositionPayoff() + printHighestProfitProbabilityPayoffs() + printMaxPositivePayoffs() + printMinNegativePayoffs()
				+ printBestRiskRewardRatioPayoffs();
	}

	private String printExistingPositionPayoff() {
		return printPayoffs("existingPositionPayoff", new OptionStrategiesWithPayoff[] { existingPositionPayoff });
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

	private String printPayoffs(String payoffName, OptionStrategiesWithPayoff[] payoffs) {
		String printPayoff = System.lineSeparator() + "***************************************" + System.lineSeparator() + payoffName + " : " + System.lineSeparator()
				+ "***************************************" + System.lineSeparator();
		if (payoffs != null && payoffs.length > 0) {
			for (OptionStrategiesWithPayoff payoff : payoffs) {
				printPayoff += payoff;
				printPayoff += System.lineSeparator();
			}
			PayoffReportXlsx.createReport(payoffName, payoffs);
		}
		return printPayoff;
	}
}
