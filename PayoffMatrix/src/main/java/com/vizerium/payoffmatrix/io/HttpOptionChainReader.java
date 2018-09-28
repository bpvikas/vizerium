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

import java.io.BufferedInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

import com.vizerium.payoffmatrix.criteria.Criteria;
import com.vizerium.payoffmatrix.option.Option;

// Reads from the TEI website
public class HttpOptionChainReader implements OptionChainReader {

	@Override
	public Option[] readOptionChain(Criteria criteria) {

		try {

			String teiOptionChainUrl = new StringBuilder(
					"=lobmys&XDITPO=tnemurtsni&71=kniLtnemges?psj.syeKnoitpo/niahc_noitpo/hctaw_evil/tnetnoCanyd/tekram_evil/moc.aidniesn.www//:sptth").reverse().toString()
					+ criteria.getUnderlyingName().replace("IN", "NI").replace("DE", "FT").replace('X', 'Y')
					+ "&date="
					+ criteria.getExpiryDate().format(DateTimeFormatter.ofPattern("dMMMyyyy")).toUpperCase();

			URL url = new URL(teiOptionChainUrl);

			BufferedInputStream bis = new BufferedInputStream(url.openStream());
			String content = "";
			int i = 0;
			while ((i = bis.read()) != -1) {
				content += (char) i;
			}

			bis.close();

			String dateString = System.getProperty("application.run.datetime");
			FileWriter fw = new FileWriter(FileUtils.directoryPath + "optionchain-localhtml/" + dateString + "_" + criteria.getUnderlyingName() + ".html");

			fw.write(content);
			fw.flush();
			fw.close();

			LocalHtmlOptionChainReader localHtmlOptionChainReader = new LocalHtmlOptionChainReader();
			return localHtmlOptionChainReader.readOptionChain(criteria);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
