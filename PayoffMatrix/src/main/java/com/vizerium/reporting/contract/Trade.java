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

import java.util.Comparator;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Trade {

	private String id;

	private String orderId;

	private String timestamp;

	private String type;

	private String segmentId;

	private String instrumentId;

	private int quantity;

	private float averagePrice;

	private float value;

	public String getId() {
		return id;
	}

	@XmlElement
	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	@XmlElement(name = "order_id")
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	@XmlElement
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	@XmlElement
	public void setType(String type) {
		this.type = type;
	}

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

	public int getQuantity() {
		return quantity;
	}

	@XmlElement
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getAveragePrice() {
		return averagePrice;
	}

	@XmlElement(name = "average_price")
	public void setAveragePrice(float averagePrice) {
		this.averagePrice = averagePrice;
	}

	public float getValue() {
		return value;
	}

	@XmlElement
	public void setValue(float value) {
		this.value = value;
	}

	static class TradeReverseTimeComparator implements Comparator<Trade> {

		@Override
		public int compare(Trade o1, Trade o2) {
			return o2.timestamp.compareTo(o1.timestamp);
		}
	}
}
