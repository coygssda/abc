package com.zomato.walletsystem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;

import com.zomato.walletsystem.model.CreateWalletModel;
import com.zomato.walletsystem.model.TransactionModel;
import com.zomato.walletsystem.model.WalletEntityModel;
import com.zomato.walletsystem.model.WalletListModel;

public class CreateWalletApplicationService {
	
	@Autowired
    private CouchbaseTemplate template;
	
	private String operation = "Created";
	
	//@Autowired
	//private WalletEntityModel walletEntityModel;
	
	@Autowired
	private TransactionModel transactionModel;
	
	@Autowired
	private WalletListModel walletListModel;

	public void createWallet(CreateWalletModel createWalletModel) {
		
		WalletEntityModel walletEntityModel = new WalletEntityModel();
		walletEntityModel.setUniqueName(createWalletModel.getUniqueName());
		walletEntityModel.setCurrency(createWalletModel.getCurrency());
		
		transactionModel.setFund(0);
		transactionModel.setOperation(operation);
		transactionModel.setUpdatedTime(LocalDateTime.now());
		
		List<TransactionModel> temp=walletEntityModel.getListOfTransaction();
		temp.add(transactionModel);
		walletEntityModel.setListOfTransaction(temp);
		
		walletListModel = template.findById(walletListModel.getWalletListId(), WalletListModel.class);
		Set<String> listIds = walletListModel.getWalletIds();
		listIds.add(createWalletModel.getUniqueName());
	
		template.save(walletListModel);
		
		
		template.save(walletEntityModel);
		
	}

}
