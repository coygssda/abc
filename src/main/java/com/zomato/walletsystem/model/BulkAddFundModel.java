package com.zomato.walletsystem.model;

public class BulkAddFundModel {

	private double fund;
	private Boolean hasExpiry;
	private String currency;
	private Integer daysOfExpiry;
	private String id;

	public double getFund() {
		return fund;
	}

	public void setFund(double fund) {
		this.fund = fund;
	}

	public Boolean getHasExpiry() {
		return hasExpiry;
	}

	public void setHasExpiry(Boolean hasExpiry) {
		this.hasExpiry = hasExpiry;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getDaysOfExpiry() {
		return daysOfExpiry;
	}

	public void setDaysOfExpiry(Integer daysOfExpiry) {
		this.daysOfExpiry = daysOfExpiry;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
