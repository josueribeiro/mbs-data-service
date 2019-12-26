package com.github.datafinance.model.service;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import com.github.datafinance.model.dao.CountryDAO;
import com.github.datafinance.model.dao.DataSerieDAO;
import com.github.datafinance.model.entity.Country;
import com.github.datafinance.model.entity.DataSerie;

public class MBSDataSerieService {
	
	@Inject
	private CountryDAO countryDAO;
	
	@Inject
	private DataSerieDAO dataSerieDAO;
	
	public List<DataSerie> getAllByCountryAndSerie(String serieCode, Integer countryCode) {
		return dataSerieDAO.getAllBYCountryAndSerie(serieCode, countryCode);
	}

	public List<DataSerie> getAllByCountryAndSerie(String serieCode, 
			Integer countryCode, Integer yearFrom, Integer yearTo) {
		return dataSerieDAO.getAllBYCountryAndSerie(serieCode, countryCode, yearFrom, yearTo);
	}

	public Collection<Country> getAllCountries() {
		return countryDAO.getAll();
	}

	public Integer getMinYearBySerie(String codeSerie) {
		return dataSerieDAO.getMinYearBySerie(codeSerie);
	}

	public Integer getMaxYearBySerie(String codeSerie) {
		return dataSerieDAO.getMaxYearBySerie(codeSerie);
	}

	public Country getCountryByID(Integer countryId) {
		return countryDAO.getById(countryId);
	}

}
