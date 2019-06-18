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

package com.vizerium.payoffmatrix.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vizerium.commons.io.FileUtils;
import com.vizerium.payoffmatrix.criteria.Criteria;
import com.vizerium.payoffmatrix.option.CallOption;
import com.vizerium.payoffmatrix.option.Option;
import com.vizerium.payoffmatrix.option.OptionType;
import com.vizerium.payoffmatrix.option.PutOption;

//Saves and reads the option chain data from a CSV file.
public class OptionCsvDataStore implements OptionDataStore {

	private static final Logger logger = Logger.getLogger(OptionCsvDataStore.class);

	@Override
	public Option[] readOptionChainData(Criteria criteria) {
		List<Option> optionChain = new ArrayList<Option>();
		BufferedReader br = null;
		try {
			File csvFile = FileUtils.getLastModifiedFileInDirectory(FileUtils.directoryPath + "optionchain-localcsv/", criteria.getUnderlyingName() + ".csv");
			if (logger.isInfoEnabled()) {
				logger.info(csvFile.getAbsolutePath());
			}
			br = new BufferedReader(new FileReader(csvFile));
			String[] headers = br.readLine().split(",");

			if (headers.length != 3) {
				throw new RuntimeException("The header line in the CSV file needs to be date,underlyingPrice,lotSize.");
			}

			LocalDate currentPremiumDate = LocalDate.parse(headers[0], DateTimeFormatter.ISO_DATE);
			float underlyingPrice = Float.parseFloat(headers[1]);
			int lotSize = Integer.parseInt(headers[2]);

			String csvLine = null;
			while ((csvLine = br.readLine()) != null) {
				String[] optionChainDetails = csvLine.split(",");
				if (optionChainDetails.length != 5) {
					throw new RuntimeException("Option Chain details obtained from CSV may not be correct. " + csvLine);
				}

				OptionType optionType = OptionType.getByProperty(optionChainDetails[1]);

				Option option;
				if (OptionType.CALL.equals(optionType)) {
					option = new CallOption();
				} else if (OptionType.PUT.equals(optionType)) {
					option = new PutOption();
				} else {
					throw new RuntimeException("Unable to recognise type of Option for " + csvLine);
				}

				option.setStrike(Float.parseFloat(optionChainDetails[0]));
				option.setCurrentPremium(Float.parseFloat(optionChainDetails[2]));
				option.setOpenInterest(Integer.parseInt(optionChainDetails[3]));
				option.setImpliedVolatility(Float.parseFloat(optionChainDetails[4]));
				option.setRiskFreeInterestRate(criteria.getRiskFreeInterestRate());
				option.setCurrentPremiumDate(currentPremiumDate);
				option.setExpiryDate(criteria.getExpiryDate());
				option.setContractSeries(criteria.getContractSeries());
				option.setUnderlyingPrice(underlyingPrice);
				option.setLotSize(lotSize);

				optionChain.add(option);
			}

		} catch (Exception e) {
			logger.error("An error occurred while reading option chain local csv data file.", e);
			throw new RuntimeException(e);
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				logger.error("An error occurred while closing option chain local csv data file.", e);
				throw new RuntimeException(e);
			}
		}

		return optionChain.toArray(new Option[optionChain.size()]);
	}

	@Override
	public void saveOptionChainData(Criteria criteria, Option[] optionChain) {
		if (optionChain == null || optionChain.length == 0) {
			return;
		}

		try {
			String dateString = System.getProperty("application.run.datetime");
			BufferedWriter bw = new BufferedWriter(new FileWriter(FileUtils.directoryPath + "optionchain-localcsv/" + dateString + "_" + criteria.getUnderlyingName() + ".csv"));

			bw.write(optionChain[0].getCurrentPremiumDate().format(DateTimeFormatter.ISO_DATE) + "," + optionChain[0].getUnderlyingPrice() + "," + optionChain[0].getLotSize());
			bw.newLine();

			for (Option option : optionChain) {
				bw.write(option.getStrike() + "," + option.getType().name() + "," + option.getCurrentPremium() + "," + option.getOpenInterest() + ","
						+ option.getImpliedVolatility());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			logger.error("An error occurred while writing option chain local csv data file.", e);
			throw new RuntimeException(e);
		}
	}
}
