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
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.io.FileUtils;

public class SuperTrendMultipleParametersTradeTestResultsParser {

	private String superTrendTestResultsDirectoryPath = FileUtils.directoryPath + "output-log-v2/";

	private static final int NO_OF_RESULT_YEARS = 9; // This is for the 9 years from 2011 to 2019.

	private static final int MAX_RESULTS_FOR_COMPARISON = 12;

	private TreeSet<ResultLine> bn15minResultLine = new TreeSet<ResultLine>(new HighestResultLineTotalComparator());

	private TreeSet<ResultLine> bn1HourResultLine = new TreeSet<ResultLine>(new HighestResultLineTotalComparator());

	private TreeSet<ResultLine> bn1DayResultLine = new TreeSet<ResultLine>(new HighestResultLineTotalComparator());

	private TreeSet<ResultLine> n15minResultLine = new TreeSet<ResultLine>(new HighestResultLineTotalComparator());

	private TreeSet<ResultLine> n1HourResultLine = new TreeSet<ResultLine>(new HighestResultLineTotalComparator());

	private TreeSet<ResultLine> n1DayResultLine = new TreeSet<ResultLine>(new HighestResultLineTotalComparator());

	private File[] getSuperTrendTestResultsFile() {
		FilenameFilter fileNameFilter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.startsWith("testrun_supertrend")) {
					return true;
				}
				return false;
			}
		};
		return new File(superTrendTestResultsDirectoryPath).listFiles(fileNameFilter);
	}

	private void compareResultLines(File[] superTrendTestResultFiles) {
		try {
			for (File superTrendTestResultFile : superTrendTestResultFiles) {
				String fileName = superTrendTestResultFile.getName();
				System.out.println("Parsing " + fileName);
				String strategyName = fileName.substring(8, fileName.lastIndexOf('.'));

				List<String> superTrendTestResultLines = new ArrayList<String>();
				BufferedReader br = new BufferedReader(new FileReader(superTrendTestResultFile));
				String s = "";
				while ((s = br.readLine()) != null) {
					superTrendTestResultLines.add(s);
				}
				br.close();

				bn15minResultLine.add(new ResultLine(strategyName, "BANKNIFTY", TimeFormat._15MIN, "T", getPointsPerYear(superTrendTestResultLines.get(2))));
				trimResultsComparisonToSize(bn15minResultLine);

				bn1HourResultLine.add(new ResultLine(strategyName, "BANKNIFTY", TimeFormat._1HOUR, "T", getPointsPerYear(superTrendTestResultLines.get(8))));
				trimResultsComparisonToSize(bn1HourResultLine);

				bn1DayResultLine.add(new ResultLine(strategyName, "BANKNIFTY", TimeFormat._1DAY, "T", getPointsPerYear(superTrendTestResultLines.get(14))));
				trimResultsComparisonToSize(bn1DayResultLine);

				n15minResultLine.add(new ResultLine(strategyName, "NIFTY", TimeFormat._15MIN, "T", getPointsPerYear(superTrendTestResultLines.get(20))));
				trimResultsComparisonToSize(n15minResultLine);

				n1HourResultLine.add(new ResultLine(strategyName, "NIFTY", TimeFormat._1HOUR, "T", getPointsPerYear(superTrendTestResultLines.get(26))));
				trimResultsComparisonToSize(n1HourResultLine);

				n1DayResultLine.add(new ResultLine(strategyName, "NIFTY", TimeFormat._1DAY, "T", getPointsPerYear(superTrendTestResultLines.get(32))));
				trimResultsComparisonToSize(n1DayResultLine);

			}
		} catch (IOException ioe) {
			throw new RuntimeException("Error while reading lines from superTrend results file.", ioe);
		}
	}

	private void trimResultsComparisonToSize(TreeSet<ResultLine> resultLineSet) {
		if (resultLineSet.size() > MAX_RESULTS_FOR_COMPARISON) {
			resultLineSet.pollLast(); // pollLast retrieves and removes the last entry in the TreeSet.
		}
	}

	private float[] getPointsPerYear(String resultLine) {
		String[] pointsPerYearString = resultLine.split(",");
		if (pointsPerYearString.length != NO_OF_RESULT_YEARS) {
			throw new RuntimeException("Did not get all records for each year." + resultLine);
		}

		float[] pointsPerYear = new float[NO_OF_RESULT_YEARS];
		for (int i = 0; i < pointsPerYearString.length; i++) {
			pointsPerYear[i] = Float.parseFloat(pointsPerYearString[i]);
		}

		return pointsPerYear;
	}

	public void parseAndCompareSuperTrendResults() {
		File[] superTrendTestResultFiles = getSuperTrendTestResultsFile();
		compareResultLines(superTrendTestResultFiles);
		printResultLines();
	}

	private void printResultLines() {
		printResultLines("bn15minResultLine", bn15minResultLine);
		printResultLines("bn1HourResultLine", bn1HourResultLine);
		printResultLines("bn1DayResultLine", bn1DayResultLine);
		printResultLines("n15minResultLine", n15minResultLine);
		printResultLines("n1HourResultLine", n1HourResultLine);
		printResultLines("n1DayResultLine", n1DayResultLine);
	}

	private void printResultLines(String resultLineSetName, TreeSet<ResultLine> resultLineSet) {
		System.out.println("********** " + resultLineSetName + " **********");
		for (ResultLine resultLine : resultLineSet) {
			System.out.println(resultLine);
		}
	}

	private static class ResultLine {

		private String strategyName;

		private String scripName;

		private TimeFormat timeFormat;

		private String plt;

		private float[] pointsPerYear = new float[NO_OF_RESULT_YEARS];

		ResultLine(String strategyName, String scripName, TimeFormat timeFormat, String plt, float[] pointsPerYear) {
			this.strategyName = strategyName;
			this.scripName = scripName;
			this.timeFormat = timeFormat;
			this.plt = plt;
			this.pointsPerYear = pointsPerYear;
		}

		public float getTotalPoints() {
			float totalPoints = 0.0f;
			for (float points : pointsPerYear) {
				totalPoints += points;
			}
			return totalPoints;
		}

		@Override
		public String toString() {
			return strategyName + "," + scripName + "," + timeFormat.toString() + "," + plt + "," + String.valueOf(getTotalPoints());
		}
	}

	private static class HighestResultLineTotalComparator implements Comparator<ResultLine> {
		@Override
		public int compare(ResultLine o1, ResultLine o2) {
			return Comparator.comparing(ResultLine::getTotalPoints).compare(o2, o1);
		}
	}

	public static void main(String[] args) {
		SuperTrendMultipleParametersTradeTestResultsParser parser = new SuperTrendMultipleParametersTradeTestResultsParser();
		parser.parseAndCompareSuperTrendResults();
	}
}
