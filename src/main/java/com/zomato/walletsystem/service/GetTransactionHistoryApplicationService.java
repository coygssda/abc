package com.zomato.walletsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;

import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.zomato.walletsystem.exception.ErrorCode;
import com.zomato.walletsystem.exception.ErrorMessage;
import com.zomato.walletsystem.exception.WalletException;
import com.zomato.walletsystem.model.TransactionListOutputModel;
import com.zomato.walletsystem.model.TransactionModel;
import com.zomato.walletsystem.model.WalletEntityModel;
import com.zomato.walletsystem.model.WalletListModel;

public class GetTransactionHistoryApplicationService {
	
	@Autowired
    private CouchbaseTemplate template;

	@Autowired
	private WalletListModel walletListModel;

	
			


	public TransactionListOutputModel getTransactionHistory(String uniqueName) throws WalletException {
		try {
			
			walletListModel = template.findById(walletListModel.getWalletListId(), WalletListModel.class);

			if(!walletListModel.getWalletIds().contains(uniqueName)) {
				throw new DocumentDoesNotExistException();

			}

			WalletEntityModel walletEntityModel  = template.findById(uniqueName, WalletEntityModel.class);

			TransactionListOutputModel x=new TransactionListOutputModel();
			List<TransactionModel> temp =walletEntityModel.getListOfTransaction();
			for(TransactionModel t:temp) {
				x.add(t);
			}
			return x;
			
		}
		catch(Exception e) {
			if(e instanceof DocumentDoesNotExistException) {
				String error = String.format(ErrorMessage.NO_WALLET_FOUND,uniqueName);
	            throw new WalletException(error, ErrorCode.BadRequest.getCode()); 
			}
			else {
				throw e;
			}
			
		}
		
	}

}
