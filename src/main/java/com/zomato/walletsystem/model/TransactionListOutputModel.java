package com.zomato.walletsystem.model;

import java.util.ArrayList;
import java.util.List;

public class TransactionListOutputModel {

	private List<TransactionModel>  listTransaction = new ArrayList<>();

	public List<TransactionModel> getListTransaction() {
		return listTransaction;
	}

	public void setListTransaction(List<TransactionModel> listTransaction) {
		this.listTransaction = listTransaction;
	}
	
	public void add(TransactionModel transactionModel) {
		this.listTransaction.add(transactionModel);
	}

	}
