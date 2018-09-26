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
import com.vizerium.payoffmatrix.engine.OptionsWithPayoff;

public class Output {

	private int analysisPayoffsLengths = 3;

	private OptionsWithPayoff[] optionsWithPayoffs;

	private OptionsWithPayoff existingPositionPayoff;

	private OptionsWithPayoff[] highestProfitProbabilityPayoffs;

	private OptionsWithPayoff[] maxPositivePayoffs;

	private OptionsWithPayoff[] minNegativePayoffs;

	private OptionsWithPayoff[] bestRiskRewardRatioPayoffs;

	public Output() {

	}

	public Output(OptionsWithPayoff[] optionsWithPayoffs) {
		super();
		this.optionsWithPayoffs = optionsWithPayoffs;
		performPayoffAnalysis();
	}

	public OptionsWithPayoff[] getOptionsWithPayoffs() {
		return optionsWithPayoffs;
	}

	public void setOptionsWithPayoffs(OptionsWithPayoff[] optionsWithPayoffs) {
		this.optionsWithPayoffs = optionsWithPayoffs;
		performPayoffAnalysis();
	}

	public void performPayoffAnalysis() {
		for (OptionsWithPayoff optionsWithPayoff : optionsWithPayoffs) {
			if (optionsWithPayoff.isExistingOpenPosition()) {
				existingPositionPayoff = optionsWithPayoff;
				break;
			}
		}

		if (analysisPayoffsLengths > optionsWithPayoffs.length) {
			analysisPayoffsLengths = optionsWithPayoffs.length;
		}

		highestProfitProbabilityPayoffs = new OptionsWithPayoff[analysisPayoffsLengths];
		maxPositivePayoffs = new OptionsWithPayoff[analysisPayoffsLengths];
		minNegativePayoffs = new OptionsWithPayoff[analysisPayoffsLengths];
		bestRiskRewardRatioPayoffs = new OptionsWithPayoff[analysisPayoffsLengths];

		Arrays.sort(optionsWithPayoffs, new HighestProfitProbabilityPayoffMatrixComparator());
		System.arraycopy(optionsWithPayoffs, 0, highestProfitProbabilityPayoffs, 0, highestProfitProbabilityPayoffs.length);
		Arrays.sort(optionsWithPayoffs, new MaximumProfitPayoffMatrixComparator());
		System.arraycopy(optionsWithPayoffs, 0, maxPositivePayoffs, 0, maxPositivePayoffs.length);
		Arrays.sort(optionsWithPayoffs, new MinimumLossPayoffMatrixComparator());
		System.arraycopy(optionsWithPayoffs, 0, minNegativePayoffs, 0, minNegativePayoffs.length);
		Arrays.sort(optionsWithPayoffs, new BestRiskRewardRatioPayoffMatrixComparator());
		System.arraycopy(optionsWithPayoffs, 0, bestRiskRewardRatioPayoffs, 0, bestRiskRewardRatioPayoffs.length);
	}

	@Override
	public String toString() {
		return "Output " + System.lineSeparator() + printExistingPositionPayoff() + printHighestProfitProbabilityPayoffs() + printMaxPositivePayoffs() + printMinNegativePayoffs()
				+ printBestRiskRewardRatioPayoffs();
	}

	private String printExistingPositionPayoff() {
		return printPayoffs("existingPositionPayoff", new OptionsWithPayoff[] { existingPositionPayoff });
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

	private String printPayoffs(String payoffName, OptionsWithPayoff[] payoffs) {
		String printPayoff = System.lineSeparator() + "***************************************" + System.lineSeparator() + payoffName + " : " + System.lineSeparator()
				+ "***************************************" + System.lineSeparator();
		if (payoffs != null && payoffs.length > 0) {
			for (OptionsWithPayoff payoff : payoffs) {
				printPayoff += payoff;
				printPayoff += System.lineSeparator();
			}
		}
		return printPayoff;
	}
}
