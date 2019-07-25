package com.zomato.walletsystem.model;

import java.time.LocalDateTime;

public class ExpiryFundModel {

	private double expiryBalance;

	public ExpiryFundModel() {
		super();
		this.expiryBalance = 0;
	}

	private LocalDateTime expiryDate;

	public double getExpiryBalance() {
		return expiryBalance;
	}

	public void setExpiryBalance(double expiryBalance) {
		this.expiryBalance = expiryBalance;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	
	
	
}
