package com.vizerium.barabanca.trade;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.vizerium.commons.dao.TimeFormat;
import com.vizerium.commons.io.FileUtils;

public class TradesReport {

	private static BufferedWriter bw = null;
	private static BufferedWriter bwTradeBook = null;

	public static void initialize() {
		try {
			File csvOutputFile = new File(FileUtils.directoryPath + "output-log-v2/testrun.csv");
			bw = new BufferedWriter(new FileWriter(csvOutputFile));
		} catch (IOException e) {
			throw new RuntimeException("Error while creating CSV file for writing P L T results.", e);
		}
		try {
			File tradeBookOutputFile = new File(FileUtils.directoryPath + "output-log-v2/tradebook.csv");
			bwTradeBook = new BufferedWriter(new FileWriter(tradeBookOutputFile));
			bwTradeBook.write(Trade.getCsvHeaderString());
			bwTradeBook.newLine();
		} catch (IOException e) {
			throw new RuntimeException("Error while creating TradeBook file for writing executed trades.", e);
		}
	}

	private static final Logger logger = Logger.getLogger(TradesReport.class);

	public static void print(TradeBook tradeBook, TimeFormat timeFormat, int startYear, int startMonth) {
		printReport(tradeBook, ReportTimeFormat._1YEAR, startYear, startMonth);
		printReport(tradeBook, ReportTimeFormat._1MONTH, startYear, startMonth);
		printAllTrades(tradeBook);
		tradeBook.printStatus(timeFormat);
	}

	private static enum ReportTimeFormat {
		_1MONTH, _1YEAR;
	}

	protected static void printReport(TradeBook tradeBook, ReportTimeFormat reportTimeFormat, int startYear, int startMonth) {
		StringBuilder p = new StringBuilder(), l = new StringBuilder(), t = new StringBuilder();

		if (ReportTimeFormat._1YEAR.equals(reportTimeFormat)) {
			int currentYear = startYear;
			TradeBook currentDurationTradeBook = new TradeBook();
			for (Trade trade : tradeBook) {
				if (trade.getExitDateTime().getYear() != currentYear) {
					updatePLT(currentDurationTradeBook, p, l, t);
					currentDurationTradeBook = new TradeBook();
					currentYear = trade.getExitDateTime().getYear();
				}
				currentDurationTradeBook.add(trade);
			}
			updatePLT(currentDurationTradeBook, p, l, t);
		} else if (ReportTimeFormat._1MONTH.equals(reportTimeFormat)) {
			int currentYear = startYear;
			int currentMonth = startMonth;
			TradeBook currentDurationTradeBook = new TradeBook();
			for (Trade trade : tradeBook) {
				if (trade.getExitDateTime().getYear() != currentYear || trade.getExitDateTime().getMonthValue() != currentMonth) {
					updatePLT(currentDurationTradeBook, p, l, t);
					while (!isAdjacentMonth(trade.getExitDateTime().getYear(), trade.getExitDateTime().getMonthValue(), currentYear, currentMonth)) {
						// The above while condition checks for those months where no trades took place at all.
						// While a similar case can be added for the years, usually there is at least one trade that is placed in a year.
						updatePLT(new TradeBook(), p, l, t);
						LocalDateTime nextMonth = getNextMonth(currentYear, currentMonth);
						currentYear = nextMonth.getYear();
						currentMonth = nextMonth.getMonthValue();
					}
					currentDurationTradeBook = new TradeBook();
					currentYear = trade.getExitDateTime().getYear();
					currentMonth = trade.getExitDateTime().getMonthValue();
				}
				currentDurationTradeBook.add(trade);
			}
			updatePLT(currentDurationTradeBook, p, l, t);
		}
		try {
			bw.write(p.toString() + System.lineSeparator() + l.toString() + System.lineSeparator() + t.toString() + System.lineSeparator());
		} catch (IOException ioe) {
			logger.error("Error while writing P L T results to CSV file.", ioe);
		}
	}

	protected static void updatePLT(TradeBook currentDurationTradeBook, StringBuilder p, StringBuilder l, StringBuilder t) {
		if (currentDurationTradeBook.size() == 0) {
			logger.debug("Updating PLT for no trades here.");
		} else {
			logger.debug("Updating PLT for " + currentDurationTradeBook.last().getEntryDateTime() + " -> " + currentDurationTradeBook.last().getExitDateTime());
		}
		p.append(currentDurationTradeBook.getProfitPayoff()).append(",");
		l.append(currentDurationTradeBook.getLossPayoff()).append(",");
		t.append(currentDurationTradeBook.getPayoff()).append(",");
	}

	private static boolean isAdjacentMonth(int currentYear, int currentMonth, int previousYear, int previousMonth) {
		if (currentYear == previousYear) {
			return currentMonth - previousMonth == 1;
		} else {
			return currentMonth - previousMonth == -11;
		}
	}

	private static LocalDateTime getNextMonth(int previousYear, int previousMonth) {
		return LocalDateTime.of(previousYear, previousMonth, 1, 6, 0).plusMonths(1);
	}

	public static void close() throws Exception {
		if (bw != null) {
			bw.flush();
			bw.close();
		}
		if (bwTradeBook != null) {
			bwTradeBook.flush();
			bwTradeBook.close();
		}
	}

	public static void printAllTrades(TradeBook tradeBook) {
		try {
			ListIterator<Trade> i = tradeBook.listIterator();
			while (i.hasNext()) {
				Trade t = i.next();
				bwTradeBook.write(t.toCsvString());
				bwTradeBook.newLine();
			}
		} catch (IOException e) {
			throw new RuntimeException("Error while creating CSV file for writing P L T results.", e);
		}
	}

	public static List<String> readFileAsString(String fileName) {

		List<String> previousResultLines = new ArrayList<String>();
		try {
			File previousResultFile = new File(fileName);
			BufferedReader br = new BufferedReader(new FileReader(previousResultFile));
			String s = "";
			while ((s = br.readLine()) != null) {
				previousResultLines.add(s);
			}
			br.close();
		} catch (IOException e) {
			throw new RuntimeException("Error while reading lines from File " + fileName, e);
		}
		return previousResultLines;
	}
}