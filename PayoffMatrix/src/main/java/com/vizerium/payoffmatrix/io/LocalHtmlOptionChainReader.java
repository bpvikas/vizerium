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

import com.vizerium.payoffmatrix.criteria.Criteria;
import com.vizerium.payoffmatrix.engine.MinimumOpenInterestCalculator;
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

			Elements underlying = htmlDoc.getElementsByTag("span");
			String underlyingPrice = underlying.get(0).text().substring(underlying.get(0).text().lastIndexOf(' ') + 1).replace(",", "");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss");
			String optionChainDateString = underlying.get(1).text().substring(6, underlying.get(1).text().lastIndexOf(" "));

			LocalDate optionChainDate = LocalDateTime.parse(optionChainDateString, formatter).toLocalDate();
			logger.info(underlyingPrice + " $$ " + optionChainDate);

			Element optionChainData = htmlDoc.getElementById("octable");

			Element optionChainDataTitle = optionChainData.select("thead").select("tr").get(1);
			Elements optionChainDataTitleElements = optionChainDataTitle.select("th");
			String headerString = "";
			for (Element e : optionChainDataTitleElements) {
				headerString += (e.text() + ",");
			}
			logger.info(headerString);

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
				String callLtp = optionChainDataRow.select("td").get(5).text().trim().replace(",", "");
				String strike = optionChainDataRow.select("td").get(11).getAllElements().get(0).getAllElements().get(0).text().trim().replace(",", "");
				String putLtp = optionChainDataRow.select("td").get(17).text().trim().replace(",", "");
				String putOI = optionChainDataRow.select("td").get(21).text().trim().replace(",", "");

				if (StringUtils.isAnyBlank(callOI, callLtp, strike, putLtp, putOI) || StringUtils.equals(callOI, "-") || StringUtils.equals(callLtp, "-")
						|| StringUtils.equals(strike, "-") || StringUtils.equals(putLtp, "-") || StringUtils.equals(putOI, "-")) {
					continue;
				}

				optionChain.add(new CallOption(strike, callOI, callLtp, underlyingPrice, optionChainDate, criteria.getLotSize()));
				optionChain.add(new PutOption(strike, putOI, putLtp, underlyingPrice, optionChainDate, criteria.getLotSize()));
			}

			if (criteria.getMinOpenInterest() <= 0) {
				criteria.setMinOpenInterest(MinimumOpenInterestCalculator.calculateMinimumOpenInterest(optionChain));
			}

			return optionChain.toArray(new Option[optionChain.size()]);
		} catch (IOException e) {
			logger.error("An error occurred while parsing option chain data locally.", e);
			throw new RuntimeException(e);
		}
	}
}
