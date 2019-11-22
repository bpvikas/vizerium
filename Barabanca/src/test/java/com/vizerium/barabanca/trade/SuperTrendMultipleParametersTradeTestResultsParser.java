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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.vizerium.barabanca.trade.TradesSummaryLine.HighestTradesSummaryLineAverageComparator;
import com.vizerium.barabanca.trade.TradesSummaryLine.HighestTradesSummaryLineTotalComparator;
import com.vizerium.commons.io.FileUtils;

public class SuperTrendMultipleParametersTradeTestResultsParser {

	private static final Logger logger = Logger.getLogger(SuperTrendMultipleParametersTradeTestResultsParser.class);

	private String superTrendTestResultsDirectoryPath = FileUtils.directoryPath + "output-log-v2/supertrend/";

	private static final int MAX_RESULTS_FOR_COMPARISON = 12;

	private String headerLine = "";

	private TreeSet<TradesSummaryLine> bn15minTradesSummaryLineTotalPayoff = new TreeSet<TradesSummaryLine>(new HighestTradesSummaryLineTotalComparator());

	private TreeSet<TradesSummaryLine> bn1HourTradesSummaryLineTotalPayoff = new TreeSet<TradesSummaryLine>(new HighestTradesSummaryLineTotalComparator());

	private TreeSet<TradesSummaryLine> bn1DayTradesSummaryLineTotalPayoff = new TreeSet<TradesSummaryLine>(new HighestTradesSummaryLineTotalComparator());

	private TreeSet<TradesSummaryLine> n15minTradesSummaryLineTotalPayoff = new TreeSet<TradesSummaryLine>(new HighestTradesSummaryLineTotalComparator());

	private TreeSet<TradesSummaryLine> n1HourTradesSummaryLineTotalPayoff = new TreeSet<TradesSummaryLine>(new HighestTradesSummaryLineTotalComparator());

	private TreeSet<TradesSummaryLine> n1DayTradesSummaryLineTotalPayoff = new TreeSet<TradesSummaryLine>(new HighestTradesSummaryLineTotalComparator());

	private TreeSet<TradesSummaryLine> bn15minTradesSummaryLineAveragePayoff = new TreeSet<TradesSummaryLine>(new HighestTradesSummaryLineAverageComparator());

	private TreeSet<TradesSummaryLine> bn1HourTradesSummaryLineAveragePayoff = new TreeSet<TradesSummaryLine>(new HighestTradesSummaryLineAverageComparator());

	private TreeSet<TradesSummaryLine> bn1DayTradesSummaryLineAveragePayoff = new TreeSet<TradesSummaryLine>(new HighestTradesSummaryLineAverageComparator());

	private TreeSet<TradesSummaryLine> n15minTradesSummaryLineAveragePayoff = new TreeSet<TradesSummaryLine>(new HighestTradesSummaryLineAverageComparator());

	private TreeSet<TradesSummaryLine> n1HourTradesSummaryLineAveragePayoff = new TreeSet<TradesSummaryLine>(new HighestTradesSummaryLineAverageComparator());

	private TreeSet<TradesSummaryLine> n1DayTradesSummaryLineAveragePayoff = new TreeSet<TradesSummaryLine>(new HighestTradesSummaryLineAverageComparator());

	private static final float BN_15MIN_AVERAGE_PAYOFF = 35.0f;
	private static final float BN_15MIN_TOTAL_PAYOFF = 29000.0f;

	private static final float BN_1HOUR_AVERAGE_PAYOFF = 70.0f;
	private static final float BN_1HOUR_TOTAL_PAYOFF = 12500.0f;

	private static final float BN_1DAY_AVERAGE_PAYOFF = 0.0f;
	private static final float BN_1DAY_TOTAL_PAYOFF = 0.0f;

	private static final float N_15MIN_AVERAGE_PAYOFF = 15.0f;
	private static final float N_15MIN_TOTAL_PAYOFF = 10000.0f;

	private static final float N_1HOUR_AVERAGE_PAYOFF = 27.0f;
	private static final float N_1HOUR_TOTAL_PAYOFF = 6300.0f;

	private static final float N_1DAY_AVERAGE_PAYOFF = 0.0f;
	private static final float N_1DAY_TOTAL_PAYOFF = 0.0f;

	private File[] getSuperTrendTestResultsFile() {
		FilenameFilter fileNameFilter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.startsWith("tradessummary_supertrend")) {
					return true;
				}
				return false;
			}
		};
		return new File(superTrendTestResultsDirectoryPath).listFiles(fileNameFilter);
	}

	private void compareResultFiles(File[] superTrendTestResultFiles) {
		try {
			for (File superTrendTestResultFile : superTrendTestResultFiles) {
				logger.info("Parsing " + superTrendTestResultFile.getName());

				BufferedReader br = new BufferedReader(new FileReader(superTrendTestResultFile));
				headerLine = "," + br.readLine(); // This is to read off the header line.

				TradesSummaryLine resultLine = new TradesSummaryLine(br.readLine());
				if (resultLine.getAveragePayoff() > BN_15MIN_AVERAGE_PAYOFF && resultLine.getTotalPayoff() > BN_15MIN_TOTAL_PAYOFF) {
					bn15minTradesSummaryLineTotalPayoff.add(resultLine);
					trimResultsComparisonToSize(bn15minTradesSummaryLineTotalPayoff);
					bn15minTradesSummaryLineAveragePayoff.add(resultLine);
					trimResultsComparisonToSize(bn15minTradesSummaryLineAveragePayoff);
				}

				resultLine = new TradesSummaryLine(br.readLine());
				if (resultLine.getAveragePayoff() > BN_1HOUR_AVERAGE_PAYOFF && resultLine.getTotalPayoff() > BN_1HOUR_TOTAL_PAYOFF) {
					bn1HourTradesSummaryLineTotalPayoff.add(resultLine);
					trimResultsComparisonToSize(bn1HourTradesSummaryLineTotalPayoff);
					bn1HourTradesSummaryLineAveragePayoff.add(resultLine);
					trimResultsComparisonToSize(bn1HourTradesSummaryLineAveragePayoff);
				}

				resultLine = new TradesSummaryLine(br.readLine());
				if (resultLine.getAveragePayoff() > BN_1DAY_AVERAGE_PAYOFF && resultLine.getTotalPayoff() > BN_1DAY_TOTAL_PAYOFF) {
					bn1DayTradesSummaryLineTotalPayoff.add(resultLine);
					trimResultsComparisonToSize(bn1DayTradesSummaryLineTotalPayoff);
					bn1DayTradesSummaryLineAveragePayoff.add(resultLine);
					trimResultsComparisonToSize(bn1DayTradesSummaryLineAveragePayoff);
				}
				resultLine = new TradesSummaryLine(br.readLine());
				if (resultLine.getAveragePayoff() > N_15MIN_AVERAGE_PAYOFF && resultLine.getTotalPayoff() > N_15MIN_TOTAL_PAYOFF) {
					n15minTradesSummaryLineTotalPayoff.add(resultLine);
					trimResultsComparisonToSize(n15minTradesSummaryLineTotalPayoff);
					n15minTradesSummaryLineAveragePayoff.add(resultLine);
					trimResultsComparisonToSize(n15minTradesSummaryLineAveragePayoff);
				}

				resultLine = new TradesSummaryLine(br.readLine());
				if (resultLine.getAveragePayoff() > N_1HOUR_AVERAGE_PAYOFF && resultLine.getTotalPayoff() > N_1HOUR_TOTAL_PAYOFF) {
					n1HourTradesSummaryLineTotalPayoff.add(resultLine);
					trimResultsComparisonToSize(n1HourTradesSummaryLineTotalPayoff);
					n1HourTradesSummaryLineAveragePayoff.add(resultLine);
					trimResultsComparisonToSize(n1HourTradesSummaryLineAveragePayoff);
				}
				resultLine = new TradesSummaryLine(br.readLine());
				if (resultLine.getAveragePayoff() > N_1DAY_AVERAGE_PAYOFF && resultLine.getTotalPayoff() > N_1DAY_TOTAL_PAYOFF) {
					n1DayTradesSummaryLineTotalPayoff.add(resultLine);
					trimResultsComparisonToSize(n1DayTradesSummaryLineTotalPayoff);
					n1DayTradesSummaryLineAveragePayoff.add(resultLine);
					trimResultsComparisonToSize(n1DayTradesSummaryLineAveragePayoff);
				}

				br.close();
			}
		} catch (IOException ioe) {
			throw new RuntimeException("Error while reading lines from superTrend results file.", ioe);
		}
	}

	private void trimResultsComparisonToSize(TreeSet<TradesSummaryLine> resultLineSet) {
		if (resultLineSet.size() > MAX_RESULTS_FOR_COMPARISON) {
			resultLineSet.pollLast(); // pollLast retrieves and removes the last entry in the TreeSet.
		}
	}

	public void parseAndCompareSuperTrendResults() {
		File[] superTrendTestResultFiles = getSuperTrendTestResultsFile();
		compareResultFiles(superTrendTestResultFiles);
		printTradesSummaryLines();
	}

	private void printTradesSummaryLines() {

		try {
			File parsedResultsFile = new File(FileUtils.directoryPath + "output-log-v2/supertrendParsedResults.csv");
			BufferedWriter bw = new BufferedWriter(new FileWriter(parsedResultsFile));
			bw.write(headerLine);
			bw.newLine();

			printTradesSummaryLines(bw, "Total", bn15minTradesSummaryLineTotalPayoff);
			printTradesSummaryLines(bw, "Total", bn1HourTradesSummaryLineTotalPayoff);
			printTradesSummaryLines(bw, "Total", bn1DayTradesSummaryLineTotalPayoff);
			printTradesSummaryLines(bw, "Total", n15minTradesSummaryLineTotalPayoff);
			printTradesSummaryLines(bw, "Total", n1HourTradesSummaryLineTotalPayoff);
			printTradesSummaryLines(bw, "Total", n1DayTradesSummaryLineTotalPayoff);
			printTradesSummaryLines(bw, "Average", bn15minTradesSummaryLineAveragePayoff);
			printTradesSummaryLines(bw, "Average", bn1HourTradesSummaryLineAveragePayoff);
			printTradesSummaryLines(bw, "Average", bn1DayTradesSummaryLineAveragePayoff);
			printTradesSummaryLines(bw, "Average", n15minTradesSummaryLineAveragePayoff);
			printTradesSummaryLines(bw, "Average", n1HourTradesSummaryLineAveragePayoff);
			printTradesSummaryLines(bw, "Average", n1DayTradesSummaryLineAveragePayoff);

			bw.flush();
			bw.close();
		} catch (IOException ioe) {
			throw new RuntimeException("Unable to write parsed results to file.", ioe);
		}
	}

	private void printTradesSummaryLines(BufferedWriter bw, String payoffType, TreeSet<TradesSummaryLine> resultLineSet) throws IOException {
		for (TradesSummaryLine resultLine : resultLineSet) {
			bw.write(payoffType + "," + resultLine);
			bw.newLine();
		}
	}

	public static void main(String[] args) {
		SuperTrendMultipleParametersTradeTestResultsParser parser = new SuperTrendMultipleParametersTradeTestResultsParser();
		parser.parseAndCompareSuperTrendResults();
	}
}
