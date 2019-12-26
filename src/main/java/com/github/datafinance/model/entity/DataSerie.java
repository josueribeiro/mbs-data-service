package com.github.datafinance.model.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MBS_DATA_SERIES")
public class DataSerie {

	@Id
	private Long id;

	@ManyToOne
	private Country country;

	@ManyToOne
	private Serie serie;
	
	@ManyToOne
	private DataCode dataCode;

	private String measure;
	private Integer periodYR;
	private Double data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public Integer getPeriodYR() {
		return periodYR;
	}
	
	public void setPeriodYR(Integer periodYR) {
		this.periodYR = periodYR;
	}

	public Double getData() {
		return data;
	}

	public void setData(Double data) {
		this.data = data;
	}
	
	public DataCode getDataCode() {
		return dataCode;
	}
	
	public void setDataCode(DataCode dataCode) {
		this.dataCode = dataCode;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataSerie other = (DataSerie) obj;
		return Objects.equals(id, other.id);
	}

}
