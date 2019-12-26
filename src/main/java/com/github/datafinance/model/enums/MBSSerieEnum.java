package com.github.datafinance.model.enums;

public enum MBSSerieEnum {
	
	POPULATION("0101", "Population"),
	PRICE_INDICES("5001", "Price Indices"),
	FINANCE_EXCHANGE_RATES("5101", "Exchange rates"),
	FINANCE_INTERNATIONAL_RESERVES_MINUS_GOLD("5301", "International reserves"),
	IMT_TOTAL_IMPORT("4501", "International Merchandise Trade: Total imports"),
	IMT_TOTAL_EXPORT("4502", "International Merchandise Trade: Total exports");

	public String CODE;
	public String DESCRIPTION;

	private MBSSerieEnum(String code, String description) {
		this.CODE = code;
		this.DESCRIPTION = description;
	}

}
