package com.zomato.walletsystem.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;

import com.zomato.walletsystem.model.ExpiryFundModel;
import com.zomato.walletsystem.model.TransactionModel;
import com.zomato.walletsystem.model.WalletEntityModel;
import com.zomato.walletsystem.model.WalletListModel;

public class BulkExpirationApplicationService {
	
	@Autowired
	private CouchbaseTemplate template;
	
	@Autowired
	private WalletListModel walletListModel;
	
	@Autowired
	private TransactionModel transactionModel;
	
	private String operation = "Removed";

	
	public void bulkRemoval() {
		
		walletListModel = template.findById(walletListModel.getWalletListId(), WalletListModel.class);

		for(String id:walletListModel.getWalletIds()) {
			
			WalletEntityModel walletEntityModel = template.findById(id, WalletEntityModel.class);

			List<ExpiryFundModel> temp = walletEntityModel.getListOfExpiryFund();
			List<ExpiryFundModel> x=new ArrayList<ExpiryFundModel>();
			x.addAll(temp);

			double expiredFund=0;
			for(ExpiryFundModel expiryFundModel : temp) {
				if(expiryFundModel.getExpiryDate().equals(LocalDateTime.now()) || expiryFundModel.getExpiryDate().isBefore(LocalDateTime.now())) {
				
					expiredFund+=expiryFundModel.getExpiryBalance();
					expiryFundModel.setExpiryBalance(0);
					
				}
				
			}
			for(int i=0;i<temp.size();i++) {
				if(temp.get(i).getExpiryBalance()==0.0) {
					temp.remove(i);
					i--;
				}
			}
			walletEntityModel.setListOfExpiryFund(temp);
			
			transactionModel.setFund(expiredFund);
			transactionModel.setOperation(operation);
			transactionModel.setUpdatedTime(LocalDateTime.now());
			
			List<TransactionModel> k=walletEntityModel.getListOfTransaction();
			k.add(transactionModel);
			walletEntityModel.setListOfTransaction(k);
			
			template.save(walletEntityModel);
		}
	}

}
