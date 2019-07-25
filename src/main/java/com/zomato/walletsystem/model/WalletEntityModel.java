package com.zomato.walletsystem.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Version;
import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

@Document
public class WalletEntityModel {
	
	@Id
	@Field
	@NotNull
	private String uniqueName;
	
	@Field
	private String currency;
	
	@Field
	private double permnanentBalance ;
	
	@Field 
	private List<ExpiryFundModel> listOfExpiryFund;
	
	@Field
	@Version
	private long version;
	
	@Field
	private List<TransactionModel> listOfTransaction;

	public WalletEntityModel() {
		super();
		this.permnanentBalance = 0;
		this.listOfExpiryFund = new ArrayList<ExpiryFundModel>();
		this.listOfTransaction = new ArrayList<TransactionModel>();
	}

	public List<TransactionModel> getListOfTransaction() {
		return listOfTransaction;
	}

	public void setListOfTransaction(List<TransactionModel> listOfTransaction) {
		this.listOfTransaction = listOfTransaction;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public List<ExpiryFundModel> getListOfExpiryFund() {
		return listOfExpiryFund;
	}

	public void setListOfExpiryFund(List<ExpiryFundModel> listOfExpiryFund) {
		this.listOfExpiryFund = listOfExpiryFund;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getPermnanentBalance() {
		return permnanentBalance;
	}

	public void setPermnanentBalance(double permnanentBalance) {
		this.permnanentBalance = permnanentBalance;
	}

	

		

}
