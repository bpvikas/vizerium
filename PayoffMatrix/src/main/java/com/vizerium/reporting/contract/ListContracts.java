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

package com.vizerium.reporting.contract;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.ArrayUtils;

import com.vizerium.payoffmatrix.io.FileUtils;

public class ListContracts {

	public static void main(String[] args) {

		try {
			File combinedContractNotes = FileUtils.getLastModifiedFileInDirectory(FileUtils.directoryPath + "contract-note/", ".xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(ContractNote.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ContractNote contractNote = (ContractNote) jaxbUnmarshaller.unmarshal(combinedContractNotes);
			// System.out.println(contractNote.getContracts().getContracts().length);

			DateTimeFormatter sourceFormatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
			DateTimeFormatter destinationFormatter = DateTimeFormatter.ofPattern("MMM dd; yyyy");
			ArrayUtils.reverse(contractNote.getContracts().getContracts());

			File contractNoteCsvFile = new File(FileUtils.directoryPath + "contract-note/" + combinedContractNotes.getName().replace(".xml", ".csv"));
			BufferedWriter bw = new BufferedWriter(new FileWriter(contractNoteCsvFile));

			for (Contract contract : contractNote.getContracts().getContracts()) {
				// System.out.println(contract.getId() + " " + contract.getName() + " " + contract.getDescription() + " " + contract.getTimestamp());
				int dayTradeNo = 0;
				for (Trade trade : contract.getTrades().getTrades()) {
					if (dayTradeNo++ == 0) {
						bw.write("," + destinationFormatter.format(sourceFormatter.parse(contract.getTimestamp())) + "," + trade.getOrderId() + "," + trade.getTimestamp() + ","
								+ trade.getId() + "," + trade.getInstrumentId().replace("NSE:", "") + "," + trade.getType().substring(0, 1).toUpperCase()
								+ trade.getType().substring(1) + "," + Math.abs(trade.getQuantity()) + "," + trade.getAveragePrice() + "," + Math.abs(trade.getValue()) + ","
								+ contract.getTotals().getGrandTotals().getBrokerage() / contract.getTrades().getTrades().length + "," + contract.getTotals().getGrandTotals());
					} else {
						bw.write("," + destinationFormatter.format(sourceFormatter.parse(contract.getTimestamp())) + "," + trade.getOrderId() + "," + trade.getTimestamp() + ","
								+ trade.getId() + "," + trade.getInstrumentId().replace("NSE:", "") + "," + trade.getType().substring(0, 1).toUpperCase()
								+ trade.getType().substring(1) + "," + Math.abs(trade.getQuantity()) + "," + trade.getAveragePrice() + "," + Math.abs(trade.getValue()) + ","
								+ contract.getTotals().getGrandTotals().getBrokerage() / contract.getTrades().getTrades().length + ",,,,,,,,,");
					}
					bw.newLine();
				}
			}

			bw.flush();
			bw.close();

		} catch (JAXBException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
