package com.zomato.walletsystem.model;

public class AddFundModel {

	private double fund;
	private Boolean hasExpiry;
	private String currency;
	private Integer daysOfExpiry;

	public Integer getDaysOfExpiry() {
		return daysOfExpiry;
	}

	public void setDaysOfExpiry(Integer daysOfExpiry) {
		this.daysOfExpiry = daysOfExpiry;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

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

}
