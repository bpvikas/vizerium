package com.vizerium.payoffmatrix.exchange;

import java.util.List;

public class Index {

	private String name;

	private List<String> stockNames;

	public Index() {
	}

	public Index(String name) {
		this.name = name;
	}

	public Index(String name, List<String> stockNames) {
		this.name = name;
		this.stockNames = stockNames;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getStockNames() {
		return stockNames;
	}

	public void setStockNames(List<String> stockNames) {
		this.stockNames = stockNames;
	}

}
