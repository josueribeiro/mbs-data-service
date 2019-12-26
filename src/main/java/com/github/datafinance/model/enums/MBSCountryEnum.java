package com.github.datafinance.model.enums;

public enum MBSCountryEnum {
	
	// BRICS
	BRAZIL(76), RUSSIAN(643), INDIA(356), CHINA(156), SOUTH_AFRICA(710),

	// ASEAN
	INDONESIA(360), MALAYSIA(458), PHILIPPINES(608), SINGAPORE(702), THAILAND(764),
	
	// NORDICS
	DENMARK(208), FINLAND(246), ICELAND(352), NORWAY(578), SWEDEN(752),
	
//	// Mercosur
//	ARGENTINA(32), PARAGUAY(600), URUGUAY(858), VENEZUELA(862),
	
	// NAFTA
	CANADA(124), UNITED_STATES(840), MEXICO(484)
	;

	public Integer CODE;

	private MBSCountryEnum(Integer idCountry) {
		this.CODE = idCountry;
	}

}
