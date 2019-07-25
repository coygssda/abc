package com.zomato.walletsystem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;

import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.zomato.walletsystem.exception.ErrorCode;
import com.zomato.walletsystem.exception.ErrorMessage;
import com.zomato.walletsystem.exception.WalletException;
import com.zomato.walletsystem.model.AddFundModel;
import com.zomato.walletsystem.model.ExpiryFundModel;
import com.zomato.walletsystem.model.TransactionModel;
import com.zomato.walletsystem.model.WalletEntityModel;
import com.zomato.walletsystem.model.WalletListModel;

public class AddFundApplicationService {
	
	@Autowired
    private CouchbaseTemplate template;
	
	private String operation = "Added";
	
	@Autowired
	private TransactionModel transactionModel;



	@Autowired
	private ExpiryFundModel expiryFundModel;
	
	@Autowired
	private WalletListModel walletListModel;
	
	public void addFund(AddFundModel addFundModel,String uniqueName) throws WalletException {
		try {
			
			walletListModel = template.findById(walletListModel.getWalletListId(), WalletListModel.class);
			
			if(!walletListModel.getWalletIds().contains(uniqueName)) {
				throw new DocumentDoesNotExistException();

			}
			else {
				WalletEntityModel walletEntityModel = template.findById(uniqueName, WalletEntityModel.class);
				if(!walletEntityModel.getCurrency().equals(addFundModel.getCurrency())) {
					/*
					 * throw exception when we can't add fund for different currency
					 */
					String error = String.format(ErrorMessage.TRANSACTION_CURRENCY_NOT_EQ_WALLET_CURRENCY,addFundModel.getCurrency(),walletEntityModel.getCurrency());
		            throw new WalletException(error, ErrorCode.BadRequest.getCode());
				}
				else {
					if(addFundModel.getHasExpiry()==true && addFundModel.getDaysOfExpiry()>0) {
						/*
						 * need to add fund in expiry
						 */
						expiryFundModel.setExpiryBalance(addFundModel.getFund());
						LocalDateTime today =  LocalDateTime.now(); 
						LocalDateTime expiryDate =  today.plusDays(addFundModel.getDaysOfExpiry()); 
						expiryFundModel.setExpiryDate(expiryDate);
						List<ExpiryFundModel> temp = walletEntityModel.getListOfExpiryFund();
						temp.add(expiryFundModel);
						walletEntityModel.setListOfExpiryFund(temp);
						
					}
					else {
						/*
						 * add fund in permanent balance
						 */
						double retrievePermanentBalance =walletEntityModel.getPermnanentBalance();
						retrievePermanentBalance = retrievePermanentBalance + addFundModel.getFund();
						walletEntityModel.setPermnanentBalance(retrievePermanentBalance);
					}
					transactionModel.setFund(addFundModel.getFund());
					transactionModel.setOperation(operation);
					transactionModel.setUpdatedTime(LocalDateTime.now());
					
					List<TransactionModel> temp=walletEntityModel.getListOfTransaction();
					temp.add(transactionModel);
					walletEntityModel.setListOfTransaction(temp);
					template.save(walletEntityModel);
				}
			}
			
		
		
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
