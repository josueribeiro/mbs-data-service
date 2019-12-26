package com.github.datafinance.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MBS_DATA_CODE")
public class DataCode {

	@Id
	private String dateCode;
	private String monthShort;
	
	@Column(name = "ORDER_MONTH")
	private Integer order;

	public String getDateCode() {
		return dateCode;
	}

	public void setDateCode(String dateCode) {
		this.dateCode = dateCode;
	}

	public String getMonthShort() {
		return monthShort;
	}

	public void setMonthShort(String monthShort) {
		this.monthShort = monthShort;
	}
	
	public Integer getOrder() {
		return order;
	}
	
	public void setOrder(Integer order) {
		this.order = order;
	}

}
