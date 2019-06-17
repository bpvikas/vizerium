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

package com.vizerium.payoffmatrix.engine;

import org.apache.log4j.Logger;

public class Analytics {

	private static final Logger logger = Logger.getLogger(Analytics.class);

	private static long positionDelta0To0Dot5 = 0L;

	private static long positionDelta0Dot5To1 = 0L;

	private static long positionDelta1To1Dot5 = 0L;

	private static long positionDelta1Dot5To2 = 0L;

	private static long positionDeltaGreaterThan2 = 0L;

	private static long positivePayoff0To50000 = 0L;

	private static long positivePayoff50000To100000 = 0L;

	private static long positivePayoff100000To150000 = 0L;

	private static long positivePayoff150000To200000 = 0L;

	private static long positivePayoffGreaterThan200000 = 0L;

	private static long positivePayoffInOIRange0To50000 = 0L;

	private static long positivePayoffInOIRange50000To100000 = 0L;

	private static long positivePayoffInOIRange100000To150000 = 0L;

	private static long positivePayoffInOIRange150000To200000 = 0L;

	private static long positivePayoffInOIRangeGreaterThan200000 = 0L;

	private static long profitProbability0To0Dot2 = 0L;

	private static long profitProbability0Dot2To0Dot4 = 0L;

	private static long profitProbability0Dot4To0Dot6 = 0L;

	private static long profitProbability0Dot6To0Dot8 = 0L;

	private static long profitProbability0Dot8To1 = 0L;

	private static long profitProbabilityInOIRange0To0Dot2 = 0L;

	private static long profitProbabilityInOIRange0Dot2To0Dot4 = 0L;

	private static long profitProbabilityInOIRange0Dot4To0Dot6 = 0L;

	private static long profitProbabilityInOIRange0Dot6To0Dot8 = 0L;

	private static long profitProbabilityInOIRange0Dot8To1 = 0L;

	private static long riskRewardRatio0To0Dot5 = 0L;

	private static long riskRewardRatio0Dot5To1 = 0L;

	private static long riskRewardRatio1To1Dot5 = 0L;

	private static long riskRewardRatio1Dot5To2 = 0L;

	private static long riskRewardRatioGreaterThan2 = 0L;

	private static long riskRewardRatioInOIRange0To0Dot5 = 0L;

	private static long riskRewardRatioInOIRange0Dot5To1 = 0L;

	private static long riskRewardRatioInOIRange1To1Dot5 = 0L;

	private static long riskRewardRatioInOIRange1Dot5To2 = 0L;

	private static long riskRewardRatioInOIRangeGreaterThan2 = 0L;

	private static long applyingCustomFilters = 0L;

	public static void write(double positionDelta, PayoffMatrix payoffMatrix) {

		double absPositionDelta = Math.abs(positionDelta);

		if (absPositionDelta >= 0 && absPositionDelta < 0.5) {
			++positionDelta0To0Dot5;
		} else if (absPositionDelta >= 0.5 && absPositionDelta < 1) {
			++positionDelta0Dot5To1;
		} else if (absPositionDelta >= 1.0 && absPositionDelta < 1.5) {
			++positionDelta1To1Dot5;
		} else if (absPositionDelta >= 1.5 && absPositionDelta < 2) {
			++positionDelta1Dot5To2;
		} else {
			++positionDeltaGreaterThan2;
		}

		float positivePayoffSum = payoffMatrix.getPositivePayoffSum();
		if (positivePayoffSum >= 0 && positivePayoffSum < 50000) {
			++positivePayoff0To50000;
		} else if (positivePayoffSum >= 50000 && positivePayoffSum < 100000) {
			++positivePayoff50000To100000;
		} else if (positivePayoffSum >= 100000 && positivePayoffSum < 150000) {
			++positivePayoff100000To150000;
		} else if (positivePayoffSum >= 150000 && positivePayoffSum < 200000) {
			++positivePayoff150000To200000;
		} else {
			++positivePayoffGreaterThan200000;
		}

		float positivePayoffSumInOIRange = payoffMatrix.getPositivePayoffSumInOIRange();
		if (positivePayoffSumInOIRange >= 0 && positivePayoffSumInOIRange < 50000) {
			++positivePayoffInOIRange0To50000;
		} else if (positivePayoffSumInOIRange >= 50000 && positivePayoffSumInOIRange < 100000) {
			++positivePayoffInOIRange50000To100000;
		} else if (positivePayoffSumInOIRange >= 100000 && positivePayoffSumInOIRange < 150000) {
			++positivePayoffInOIRange100000To150000;
		} else if (positivePayoffSumInOIRange >= 150000 && positivePayoffSumInOIRange < 200000) {
			++positivePayoffInOIRange150000To200000;
		} else {
			++positivePayoffInOIRangeGreaterThan200000;
		}

		float profitProbability = payoffMatrix.getProfitProbability();
		if (profitProbability >= 0 && profitProbability < 0.2) {
			++profitProbability0To0Dot2;
		} else if (profitProbability >= 0.2 && profitProbability < 0.4) {
			++profitProbability0Dot2To0Dot4;
		} else if (profitProbability >= 0.4 && profitProbability < 0.6) {
			++profitProbability0Dot4To0Dot6;
		} else if (profitProbability >= 0.6 && profitProbability < 0.8) {
			++profitProbability0Dot6To0Dot8;
		} else {
			++profitProbability0Dot8To1;
		}

		float profitProbabilityInOIRange = payoffMatrix.getProfitProbabilityInOIRange();
		if (profitProbabilityInOIRange >= 0 && profitProbabilityInOIRange < 0.2) {
			++profitProbabilityInOIRange0To0Dot2;
		} else if (profitProbabilityInOIRange >= 0.2 && profitProbabilityInOIRange < 0.4) {
			++profitProbabilityInOIRange0Dot2To0Dot4;
		} else if (profitProbabilityInOIRange >= 0.4 && profitProbabilityInOIRange < 0.6) {
			++profitProbabilityInOIRange0Dot4To0Dot6;
		} else if (profitProbabilityInOIRange >= 0.6 && profitProbabilityInOIRange < 0.8) {
			++profitProbabilityInOIRange0Dot6To0Dot8;
		} else {
			++profitProbabilityInOIRange0Dot8To1;
		}

		float riskRewardRatio = payoffMatrix.getRiskRewardRatio();
		if (riskRewardRatio >= 0 && riskRewardRatio < 0.5) {
			++riskRewardRatio0To0Dot5;
		} else if (riskRewardRatio >= 0.5 && riskRewardRatio < 1) {
			++riskRewardRatio0Dot5To1;
		} else if (riskRewardRatio >= 1.0 && riskRewardRatio < 1.5) {
			++riskRewardRatio1To1Dot5;
		} else if (riskRewardRatio >= 1.5 && riskRewardRatio < 2) {
			++riskRewardRatio1Dot5To2;
		} else {
			++riskRewardRatioGreaterThan2;
		}

		float riskRewardRatioInOIRange = payoffMatrix.getRiskRewardRatioInOIRange();
		if (riskRewardRatioInOIRange >= 0 && riskRewardRatioInOIRange < 0.5) {
			++riskRewardRatioInOIRange0To0Dot5;
		} else if (riskRewardRatioInOIRange >= 0.5 && riskRewardRatioInOIRange < 1) {
			++riskRewardRatioInOIRange0Dot5To1;
		} else if (riskRewardRatioInOIRange >= 1.0 && riskRewardRatioInOIRange < 1.5) {
			++riskRewardRatioInOIRange1To1Dot5;
		} else if (riskRewardRatioInOIRange >= 1.5 && riskRewardRatioInOIRange < 2) {
			++riskRewardRatioInOIRange1Dot5To2;
		} else {
			++riskRewardRatioInOIRangeGreaterThan2;
		}

		if (applyingCustomFilters(absPositionDelta, payoffMatrix)) {
			++applyingCustomFilters;
		}
	}

	public static void getTrends() {
		if (logger.isInfoEnabled()) {
			logger.info("");
			logger.info("delta 0.0 -> 0.5 " + positionDelta0To0Dot5);
			logger.info("delta 0.5 -> 1.0 " + positionDelta0Dot5To1);
			logger.info("delta 1.0 -> 1.5 " + positionDelta1To1Dot5);
			logger.info("delta 1.5 -> 2.0 " + positionDelta1Dot5To2);
			logger.info("delta 2.0 ->     " + positionDeltaGreaterThan2);
			logger.info("");
			logger.info("+ve payoff  00000 ->  50000 " + positivePayoff0To50000);
			logger.info("+ve payoff  50000 -> 100000 " + positivePayoff50000To100000);
			logger.info("+ve payoff 100000 -> 150000 " + positivePayoff100000To150000);
			logger.info("+ve payoff 150000 -> 200000 " + positivePayoff150000To200000);
			logger.info("+ve payoff 200000 ->        " + positivePayoffGreaterThan200000);
			logger.info("");
			logger.info("+ve payoff in OI Range  00000 ->  50000 " + positivePayoffInOIRange0To50000);
			logger.info("+ve payoff in OI Range  50000 -> 100000 " + positivePayoffInOIRange50000To100000);
			logger.info("+ve payoff in OI Range 100000 -> 150000 " + positivePayoffInOIRange100000To150000);
			logger.info("+ve payoff in OI Range 150000 -> 200000 " + positivePayoffInOIRange150000To200000);
			logger.info("+ve payoff in OI Range 200000 ->        " + positivePayoffInOIRangeGreaterThan200000);
			logger.info("");
			logger.info("profit probability 0.0 -> 0.2 " + profitProbability0To0Dot2);
			logger.info("profit probability 0.2 -> 0.4 " + profitProbability0Dot2To0Dot4);
			logger.info("profit probability 0.4 -> 0.6 " + profitProbability0Dot4To0Dot6);
			logger.info("profit probability 0.6 -> 0.8 " + profitProbability0Dot6To0Dot8);
			logger.info("profit probability 0.8 -> 1.0 " + profitProbability0Dot8To1);
			logger.info("");
			logger.info("profit probability in OI Range 0.0 -> 0.2 " + profitProbabilityInOIRange0To0Dot2);
			logger.info("profit probability in OI Range 0.2 -> 0.4 " + profitProbabilityInOIRange0Dot2To0Dot4);
			logger.info("profit probability in OI Range 0.4 -> 0.6 " + profitProbabilityInOIRange0Dot4To0Dot6);
			logger.info("profit probability in OI Range 0.6 -> 0.8 " + profitProbabilityInOIRange0Dot6To0Dot8);
			logger.info("profit probability in OI Range 0.8 -> 1.0 " + profitProbabilityInOIRange0Dot8To1);
			logger.info("");
			logger.info("RRR 0.0 -> 0.5 " + riskRewardRatio0To0Dot5);
			logger.info("RRR 0.5 -> 1.0 " + riskRewardRatio0Dot5To1);
			logger.info("RRR 1.0 -> 1.5 " + riskRewardRatio1To1Dot5);
			logger.info("RRR 1.5 -> 2.0 " + riskRewardRatio1Dot5To2);
			logger.info("RRR 2.0 ->     " + riskRewardRatioGreaterThan2);
			logger.info("");
			logger.info("RRR in OI Range 0.0 -> 0.5 " + riskRewardRatioInOIRange0To0Dot5);
			logger.info("RRR in OI Range 0.5 -> 1.0 " + riskRewardRatioInOIRange0Dot5To1);
			logger.info("RRR in OI Range 1.0 -> 1.5 " + riskRewardRatioInOIRange1To1Dot5);
			logger.info("RRR in OI Range 1.5 -> 2.0 " + riskRewardRatioInOIRange1Dot5To2);
			logger.info("RRR in OI Range 2.0 ->     " + riskRewardRatioInOIRangeGreaterThan2);
			logger.info("");
			logger.info("Applying multiple custom filters " + applyingCustomFilters);
			logger.info(customFilters);
			logger.info("");
		}
	}

	public static boolean applyingCustomFilters(double positionDelta, PayoffMatrix payoffMatrix) {
		// @formatter:off
		return (positionDelta > customFilters.minPositionDelta)
				&& (positionDelta < customFilters.maxPositionDelta)
				&& (payoffMatrix.getPositivePayoffSum() > customFilters.minPositivePayoffSum)
				&& (payoffMatrix.getPositivePayoffSumInOIRange() > customFilters.minPositivePayoffSumInOIRange) 
				&& (payoffMatrix.getProfitProbability() > customFilters.minProfitProbability)
				&& (payoffMatrix.getProfitProbabilityInOIRange() > customFilters.minProfitProbabilityInOIRange) 
				&& (payoffMatrix.getRiskRewardRatio() < customFilters.maxRiskRewardRatio) 
				&& (payoffMatrix.getRiskRewardRatioInOIRange() < customFilters.maxRiskRewardRatioInOIRange)
				;
		// @formatter:on
	}

	private static CustomFilters customFilters;

	public static void setCustomFilters(CustomFilters customFilters) {
		Analytics.customFilters = customFilters;
	}

	public static class CustomFilters {

		private double minPositionDelta;
		private double maxPositionDelta;
		private float minPositivePayoffSum;
		private float minPositivePayoffSumInOIRange;
		private float minProfitProbability;
		private float minProfitProbabilityInOIRange;
		private float maxRiskRewardRatio;
		private float maxRiskRewardRatioInOIRange;

		public CustomFilters(double minPositionDelta, double maxPositionDelta, float minPositivePayoffSum, float minPositivePayoffSumInOIRange, float minProfitProbability,
				float minProfitProbabilityInOIRange, float maxRiskRewardRatio, float maxRiskRewardRatioInOIRange) {
			if (minPositionDelta > maxPositionDelta) {
				throw new RuntimeException("Min position delta " + minPositionDelta + " cannot be greater than max position delta " + maxPositionDelta);
			}
			if (minPositivePayoffSum < 0.0f) {
				throw new RuntimeException("Min positive payoff sum " + minPositivePayoffSum + " needs to be zero or higher.");
			}
			if (minPositivePayoffSumInOIRange < 0.0f) {
				throw new RuntimeException("Min positive payoff sum in OI range " + minPositivePayoffSumInOIRange + " needs to be zero or higher.");
			}
			if (minProfitProbability < 0.0f || minProfitProbability > 1.0f) {
				throw new RuntimeException("Min profit probability " + minProfitProbability + " needs to be between 0 and 1.");
			}
			if (minProfitProbabilityInOIRange < 0.0f || minProfitProbabilityInOIRange > 1.0f) {
				throw new RuntimeException("Min profit probability in OI range " + minProfitProbabilityInOIRange + " needs to be between 0 and 1.");
			}
			if (maxRiskRewardRatio < 0.0f) {
				throw new RuntimeException("Max risk reward ratio " + maxRiskRewardRatio + " cannot be -ve.");
			}
			if (maxRiskRewardRatioInOIRange < 0.0f) {
				throw new RuntimeException("Max risk reward ratio in OI range " + maxRiskRewardRatioInOIRange + " cannot be -ve.");
			}

			this.minPositionDelta = minPositionDelta;
			this.maxPositionDelta = maxPositionDelta;
			this.minPositivePayoffSum = minPositivePayoffSum;
			this.minPositivePayoffSumInOIRange = minPositivePayoffSumInOIRange;
			this.minProfitProbability = minProfitProbability;
			this.minProfitProbabilityInOIRange = minProfitProbabilityInOIRange;
			this.maxRiskRewardRatio = maxRiskRewardRatio;
			this.maxRiskRewardRatioInOIRange = maxRiskRewardRatioInOIRange;
		}

		@Override
		public String toString() {
			return "CustomFilters [minPositionDelta=" + minPositionDelta + ", maxPositionDelta=" + maxPositionDelta + ", min+vePayoffSum=" + minPositivePayoffSum
					+ ", min+vePayoffSumOIRange=" + minPositivePayoffSumInOIRange + ", minProfitProb=" + minProfitProbability + ", minProfitProbOIRange="
					+ minProfitProbabilityInOIRange + ", maxRRR=" + maxRiskRewardRatio + ", maxRRROIRange=" + maxRiskRewardRatioInOIRange + "]";
		}
	}
}
