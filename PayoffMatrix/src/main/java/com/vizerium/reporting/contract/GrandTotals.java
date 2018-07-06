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

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

public class GrandTotals {

	private GrandTotal[] grandTotals;

	private Map<String, String> grandTotalChargesMap;

	public GrandTotal[] getGrandTotals() {
		return grandTotals;
	}

	@XmlElement(name = "grandtotal")
	public void setGrandTotals(GrandTotal[] grandTotals) {
		this.grandTotals = grandTotals;
		if (null == grandTotalChargesMap) {
			grandTotalChargesMap = new HashMap<String, String>();
		}
		for (GrandTotal grandTotal : grandTotals) {
			grandTotalChargesMap.put(grandTotal.getName(), grandTotal.getValue());
		}
	}

	public float getBrokerage() {
		return Float.parseFloat(grandTotalChargesMap.get("Brokerage"));
	}

	public String toString() {
		return grandTotalChargesMap.get("Securities Transaction Tax") + "," + grandTotalChargesMap.get("Exchange Transaction Charges") + ","
				+ grandTotalChargesMap.get("Clearing Charges") + "," + grandTotalChargesMap.get("Integrated GST") + "," + grandTotalChargesMap.get("Central GST") + ","
				+ grandTotalChargesMap.get("State GST") + "," + grandTotalChargesMap.get("SEBI Turnover Fees") + "," + grandTotalChargesMap.get("Stamp Duty") + ",";
	}
}
