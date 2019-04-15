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

package com.vizerium.reporting.contract;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class SubTotal {

	private String segmentId;

	private String instrumentId;

	private String description;

	private String quantity;

	private String averagePrice;

	private String value;

	public String getSegmentId() {
		return segmentId;
	}

	@XmlAttribute(name = "segment_id")
	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	@XmlAttribute(name = "instrument_id")
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public String getDescription() {
		return description;
	}

	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuantity() {
		return quantity;
	}

	@XmlElement
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getAveragePrice() {
		return averagePrice;
	}

	@XmlElement
	public void setAveragePrice(String averagePrice) {
		this.averagePrice = averagePrice;
	}

	public String getValue() {
		return value;
	}

	@XmlElement
	public void setValue(String value) {
		this.value = value;
	}

}
