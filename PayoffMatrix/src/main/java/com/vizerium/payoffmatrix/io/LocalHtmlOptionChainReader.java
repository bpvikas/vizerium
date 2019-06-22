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

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.vizerium.commons.io.FileUtils;
import com.vizerium.payoffmatrix.criteria.Criteria;
import com.vizerium.payoffmatrix.engine.OpenInterestAnalytics;
import com.vizerium.payoffmatrix.option.CallOption;
import com.vizerium.payoffmatrix.option.Option;
import com.vizerium.payoffmatrix.option.PutOption;

//Reads from the local html file copied locally
public class LocalHtmlOptionChainReader implements OptionChainReader {

	private static final Logger logger = Logger.getLogger(LocalHtmlOptionChainReader.class);

	@Override
	public Option[] readOptionChain(Criteria criteria) {
		try {
			File localHtmlFile = FileUtils.getLastModifiedFileInDirectory(FileUtils.directoryPath + "optionchain-localhtml/", criteria.getUnderlyingName() + ".html");

			Document htmlDoc = Jsoup.parse(localHtmlFile, "UTF-8");

			Elements spanElements = htmlDoc.getElementsByTag("span");
			String underlyingPriceString = "";
			String optionChainDateString = "";
			for (Element e : spanElements) {
				if (e.text().contains("Underlying Index")) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.text());
					}
					underlyingPriceString = e.text().substring(e.text().lastIndexOf(' ') + 1).replace(",", "");
				} else if (e.text().contains("IST")) {
					if (logger.isDebugEnabled()) {
						logger.debug(e.text());
					}
					optionChainDateString = e.text().substring(6, e.text().lastIndexOf(" "));
				}
				if (!StringUtils.isAnyBlank(underlyingPriceString, optionChainDateString)) {
					break;
				}
			}
			if (StringUtils.isAnyBlank(underlyingPriceString, optionChainDateString)) {
				throw new RuntimeException("Unable to determine underlyingPrice: " + underlyingPriceString + " or optionChainDateString: " + optionChainDateString);
			}

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss");
			LocalDateTime optionChainDateTime = LocalDateTime.parse(optionChainDateString, formatter);
			LocalDate optionChainDate = optionChainDateTime.toLocalDate();
			float underlyingPrice = Float.parseFloat(underlyingPriceString);
			if (logger.isInfoEnabled()) {
				logger.info(underlyingPrice + " $$ " + optionChainDate + " " + optionChainDateTime.toLocalTime());
			}
			if (!optionChainDate.equals(criteria.getHistoricalDataDateRange().getEndDate())) {
				throw new RuntimeException(
						"The option chain date " + optionChainDate + " does not match the historical data end date " + criteria.getHistoricalDataDateRange().getEndDate());
			}
			if (Math.abs(underlyingPrice - criteria.getUnderlyingValue()) / criteria.getUnderlyingValue() >= 0.0025f) {
				throw new RuntimeException("There is more than a 0.25% difference between the Option Chain price " + underlyingPrice
						+ " and the historical data last closing Price " + criteria.getUnderlyingValue());
			}

			Element optionChainData = htmlDoc.getElementById("octable");

			Element optionChainDataTitle = optionChainData.select("thead").select("tr").get(1);
			Elements optionChainDataTitleElements = optionChainDataTitle.select("th");
			String headerString = "";
			for (Element e : optionChainDataTitleElements) {
				headerString += (e.text() + ",");
			}
			if (logger.isDebugEnabled()) {
				logger.debug(headerString);
			}
			List<Option> optionChain = new ArrayList<Option>();

			Elements optionChainDataRows = optionChainData.select("tr");
			int rowDataCount = 0;
			for (Element optionChainDataRow : optionChainDataRows) {

				if (rowDataCount++ <= 1) {
					continue;
				}

				if (optionChainDataRow.select("td").get(0).text().trim().equalsIgnoreCase("Total")) {
					break;
				}

				String callOI = optionChainDataRow.select("td").get(1).text().trim().replace(",", "");
				String callOIChange = optionChainDataRow.select("td").get(2).text().trim().replace(",", "");
				String callIV = optionChainDataRow.select("td").get(4).text().trim().replace(",", "");
				String callLtp = optionChainDataRow.select("td").get(5).text().trim().replace(",", "");
				String strike = optionChainDataRow.select("td").get(11).getAllElements().get(0).getAllElements().get(0).text().trim().replace(",", "");
				String putLtp = optionChainDataRow.select("td").get(17).text().trim().replace(",", "");
				String putIV = optionChainDataRow.select("td").get(18).text().trim().replace(",", "");
				String putOIChange = optionChainDataRow.select("td").get(20).text().trim().replace(",", "");
				String putOI = optionChainDataRow.select("td").get(21).text().trim().replace(",", "");

				if (StringUtils.isNoneBlank(callOI, callOIChange, callIV, callLtp, strike) && !StringUtils.equals(callOI, "-") && (Integer.parseInt(callOI) >= 3000)
						&& !StringUtils.equals(callOIChange, "-") && !StringUtils.equals(callIV, "-") && !StringUtils.equals(callLtp, "-") && !StringUtils.equals(strike, "-")) {
					// Adding the 3000 check here as there are many options in the Index option chain which are in few 100s and disturb the minimum Open Interest calculation
					optionChain
							.add(new CallOption(strike, callOI, callOIChange, callLtp, Float.parseFloat(callIV) / 100.0f, underlyingPrice, optionChainDate, criteria.getLotSize()));
				}

				if (StringUtils.isNoneBlank(strike, putLtp, putIV, putOIChange, putOI) && !StringUtils.equals(strike, "-") && !StringUtils.equals(putLtp, "-")
						&& !StringUtils.equals(putIV, "-") && !StringUtils.equals(putOIChange, "-") && !StringUtils.equals(putOI, "-") && (Integer.parseInt(putOI) >= 3000)) {
					// Adding the 3000 check here as there are many options in the Index option chain which are in few 100s and disturb the minimum Open Interest calculation
					optionChain.add(new PutOption(strike, putOI, putOIChange, putLtp, Float.parseFloat(putIV) / 100.0f, underlyingPrice, optionChainDate, criteria.getLotSize()));
				}
			}

			OpenInterestAnalytics oiAnalytics = new OpenInterestAnalytics(optionChain);
			if (criteria.getMinOpenInterest() <= 0) {
				criteria.setMinOpenInterest(oiAnalytics.calculateMinimumOpenInterest());
			}
			criteria.setOIBasedRange(oiAnalytics.getOIBasedRange(criteria.getUnderlyingRangeStep()));
			oiAnalytics.calculatePCR();
			oiAnalytics.calculatePCR(10);
			oiAnalytics.getMaxOpenInterestAdditions();
			oiAnalytics.getMaxOpenInterestExits();

			return optionChain.toArray(new Option[optionChain.size()]);
		} catch (IOException e) {
			logger.error("An error occurred while parsing option chain data locally.", e);
			throw new RuntimeException(e);
		}
	}
}
