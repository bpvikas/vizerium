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

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.TreeSet;

import com.vizerium.commons.trade.TradeAction;
import com.vizerium.payoffmatrix.comparator.BestRiskRewardRatioPayoffMatrixComparator;
import com.vizerium.payoffmatrix.comparator.HighestAveragePayoffMatrixComparator;
import com.vizerium.payoffmatrix.comparator.HighestProfitProbabilityPayoffMatrixComparator;
import com.vizerium.payoffmatrix.comparator.MaximumProfitPayoffMatrixComparator;
import com.vizerium.payoffmatrix.comparator.MinimumLossPayoffMatrixComparator;
import com.vizerium.payoffmatrix.comparator.MostDeltaNeutralPayoffMatrixComparator;
import com.vizerium.payoffmatrix.engine.OptionStrategiesWithPayoff;
import com.vizerium.payoffmatrix.engine.PayoffCalculator;
import com.vizerium.payoffmatrix.option.Option;
import com.vizerium.payoffmatrix.option.OptionStrategy;
import com.vizerium.payoffmatrix.option.OptionType;
import com.vizerium.payoffmatrix.reports.PayoffReportXlsx;
import com.vizerium.payoffmatrix.volatility.Range;

public class Output {

	public static int analysisPayoffsLengths = 7;

	private long countAnalysedOptionStrategies = 0L;

	private LocalTime analysisStartTime = null;

	private LocalTime analysisEndTime = null;

	private OptionStrategiesWithPayoff existingPositionPayoff;

	private TreeSet<OptionStrategiesWithPayoff> highestProfitProbabilityPayoffs = new TreeSet<OptionStrategiesWithPayoff>(new HighestProfitProbabilityPayoffMatrixComparator());

	private TreeSet<OptionStrategiesWithPayoff> maxPositivePayoffs = new TreeSet<OptionStrategiesWithPayoff>(new MaximumProfitPayoffMatrixComparator());

	private TreeSet<OptionStrategiesWithPayoff> minNegativePayoffs = new TreeSet<OptionStrategiesWithPayoff>(new MinimumLossPayoffMatrixComparator());

	private TreeSet<OptionStrategiesWithPayoff> bestRiskRewardRatioPayoffs = new TreeSet<OptionStrategiesWithPayoff>(new BestRiskRewardRatioPayoffMatrixComparator());

	private TreeSet<OptionStrategiesWithPayoff> highestAveragePayoffs = new TreeSet<OptionStrategiesWithPayoff>(new HighestAveragePayoffMatrixComparator());

	private TreeSet<OptionStrategiesWithPayoff> mostDeltaNeutralPayoffs = new TreeSet<OptionStrategiesWithPayoff>(new MostDeltaNeutralPayoffMatrixComparator());

	private String underlyingName;

	private Range underlyingRange;

	private float underlyingCurrentPrice;

	private int optionStrategiesCount;

	public Output() {
		analysisStartTime = LocalTime.now();
	}

	public void performPayoffAnalysis(OptionStrategiesWithPayoff optionStrategiesWithPayoff) {
		++countAnalysedOptionStrategies;
		if (optionStrategiesWithPayoff.isExistingOpenPosition()) {
			existingPositionPayoff = optionStrategiesWithPayoff;
		}

		if (!allOptionsInStrategyAreOfSameOrientation(optionStrategiesWithPayoff.getOptions())) {
			compareToExistingPayoffs(highestAveragePayoffs, optionStrategiesWithPayoff);

			compareToExistingPayoffs(highestProfitProbabilityPayoffs, optionStrategiesWithPayoff);

			compareToExistingPayoffs(maxPositivePayoffs, optionStrategiesWithPayoff);

			if (optionStrategiesWithPayoff.getNegativePayoffSum() < -2.0f) {
				compareToExistingPayoffs(minNegativePayoffs, optionStrategiesWithPayoff);
			}

			compareToExistingPayoffs(bestRiskRewardRatioPayoffs, optionStrategiesWithPayoff);

			compareToExistingPayoffs(mostDeltaNeutralPayoffs, optionStrategiesWithPayoff);
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

	public void setUnderlyingCurrentPrice(float underlyingCurrentPrice) {
		this.underlyingCurrentPrice = underlyingCurrentPrice;
	}

	public int getOptionStrategiesCount() {
		return optionStrategiesCount;
	}

	public void setOptionStrategiesCount(int optionStrategiesCount) {
		this.optionStrategiesCount = optionStrategiesCount;
	}

	private boolean allOptionsInStrategyAreOfSameOrientation(OptionStrategy[] optionStrategies) {
		TradeAction firstTradeOrientation = null;

		for (OptionStrategy optionStrategy : optionStrategies) {
			for (Option option : optionStrategy.getOptions()) {
				if (firstTradeOrientation == null) {
					firstTradeOrientation = getOrientation(option.getType(), option.getTradeAction());
				}
				if (!(getOrientation(option.getType(), option.getTradeAction()).equals(firstTradeOrientation))) {
					return false;
				}
			}
		}
		return true;
	}

	private TradeAction getOrientation(OptionType optionType, TradeAction tradeAction) {

		if (optionType.equals(OptionType.CALL) && tradeAction.equals(TradeAction.LONG)) {
			return TradeAction.LONG;
		} else if (optionType.equals(OptionType.CALL) && tradeAction.equals(TradeAction.SHORT)) {
			return TradeAction.SHORT;
		} else if (optionType.equals(OptionType.PUT) && tradeAction.equals(TradeAction.LONG)) {
			return TradeAction.SHORT;
		} else if (optionType.equals(OptionType.PUT) && tradeAction.equals(TradeAction.SHORT)) {
			return TradeAction.LONG;
		} else {
			throw new RuntimeException("Unable to determine orientation from " + optionType + " and " + tradeAction + ".");
		}
	}

	@Override
	public String toString() {
		return "Output - Analysed " + countAnalysedOptionStrategies + " in " + ChronoUnit.SECONDS.between(analysisStartTime, analysisEndTime) + " seconds, after eliminating "
				+ PayoffCalculator.countOptionWithOppositeActions + " same option with opposite actions." + System.lineSeparator() + printExistingPositionPayoff()
				+ printHighestProfitProbabilityPayoffs() + printMaxPositivePayoffs() + printMinNegativePayoffs() + printBestRiskRewardRatioPayoffs() + printHighestAveragePayoffs()
				+ printMostDeltaNeutralPayoffs();
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

	private String printMostDeltaNeutralPayoffs() {
		return printPayoffs("mostDeltaNeutralPayoffs", mostDeltaNeutralPayoffs);
	}

	private String printPayoffs(String payoffName, TreeSet<OptionStrategiesWithPayoff> payoffs) {
		String printPayoff = System.lineSeparator() + "***************************************" + System.lineSeparator() + underlyingName + " " + payoffName + " : "
				+ System.lineSeparator() + "***************************************" + System.lineSeparator();
		if (payoffs != null && payoffs.size() > 0) {
			for (OptionStrategiesWithPayoff payoff : payoffs) {
				printPayoff += payoff;
				printPayoff += System.lineSeparator();
			}
			PayoffReportXlsx.createReport(underlyingName, payoffName, payoffs, optionStrategiesCount, underlyingRange, underlyingCurrentPrice);
		}
		return printPayoff;
	}

	public void finale() {
		analysisEndTime = LocalTime.now();
	}
}
