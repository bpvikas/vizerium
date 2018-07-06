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

import com.vizerium.payoffmatrix.comparator.BearCaseHighestProfitProbabilityPayoffMatrixComparator;
import com.vizerium.payoffmatrix.comparator.BearCaseMaximumProfitPayoffMatrixComparator;
import com.vizerium.payoffmatrix.comparator.BearCaseMinimumLossPayoffMatrixComparator;
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

	private OptionsWithPayoff[] bearCaseHighestProfitProbabilityPayoffs;

	private OptionsWithPayoff[] bearCaseMaxPositivePayoffs;

	private OptionsWithPayoff[] bearCaseMinNegativePayoffs;

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

		bearCaseHighestProfitProbabilityPayoffs = new OptionsWithPayoff[analysisPayoffsLengths];
		bearCaseMaxPositivePayoffs = new OptionsWithPayoff[analysisPayoffsLengths];
		bearCaseMinNegativePayoffs = new OptionsWithPayoff[analysisPayoffsLengths];

		Arrays.sort(optionsWithPayoffs, new HighestProfitProbabilityPayoffMatrixComparator());
		System.arraycopy(optionsWithPayoffs, 0, highestProfitProbabilityPayoffs, 0, highestProfitProbabilityPayoffs.length);
		Arrays.sort(optionsWithPayoffs, new MaximumProfitPayoffMatrixComparator());
		System.arraycopy(optionsWithPayoffs, 0, maxPositivePayoffs, 0, maxPositivePayoffs.length);
		Arrays.sort(optionsWithPayoffs, new MinimumLossPayoffMatrixComparator());
		System.arraycopy(optionsWithPayoffs, 0, minNegativePayoffs, 0, minNegativePayoffs.length);

		Arrays.sort(optionsWithPayoffs, new BearCaseHighestProfitProbabilityPayoffMatrixComparator());
		System.arraycopy(optionsWithPayoffs, 0, bearCaseHighestProfitProbabilityPayoffs, 0, bearCaseHighestProfitProbabilityPayoffs.length);
		Arrays.sort(optionsWithPayoffs, new BearCaseMaximumProfitPayoffMatrixComparator());
		System.arraycopy(optionsWithPayoffs, 0, bearCaseMaxPositivePayoffs, 0, bearCaseMaxPositivePayoffs.length);
		Arrays.sort(optionsWithPayoffs, new BearCaseMinimumLossPayoffMatrixComparator());
		System.arraycopy(optionsWithPayoffs, 0, bearCaseMinNegativePayoffs, 0, bearCaseMinNegativePayoffs.length);
	}

	@Override
	public String toString() {
		return "Output " + System.lineSeparator() + printExistingPositionPayoff() + printHighestProfitProbabilityPayoffs() + printMaxPositivePayoffs() + printMinNegativePayoffs()
				+ printBearCaseHighestProfitProbabilityPayoffs() + printBearCaseMaxPositivePayoffs() + printBearCaseMinNegativePayoffs();
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

	private String printBearCaseHighestProfitProbabilityPayoffs() {
		return printPayoffs("bearCaseHighestProfitProbabilityPayoffs", bearCaseHighestProfitProbabilityPayoffs);
	}

	private String printBearCaseMaxPositivePayoffs() {
		return printPayoffs("bearCaseMaxPositivePayoffs", bearCaseMaxPositivePayoffs);
	}

	private String printBearCaseMinNegativePayoffs() {
		return printPayoffs("bearCaseMinNegativePayoffs", bearCaseMinNegativePayoffs);
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
