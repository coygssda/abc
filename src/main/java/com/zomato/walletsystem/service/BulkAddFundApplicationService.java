package com.zomato.walletsystem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;

import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.zomato.walletsystem.exception.ErrorCode;
import com.zomato.walletsystem.exception.ErrorMessage;
import com.zomato.walletsystem.exception.WalletException;
import com.zomato.walletsystem.model.BulkAddFundModel;
import com.zomato.walletsystem.model.ExpiryFundModel;
import com.zomato.walletsystem.model.TransactionModel;
import com.zomato.walletsystem.model.WalletEntityModel;
import com.zomato.walletsystem.model.WalletListModel;

public class BulkAddFundApplicationService {

	@Autowired
	private CouchbaseTemplate template;

	private String operation = "Added";

	@Autowired
	private TransactionModel transactionModel;

	@Autowired
	private ExpiryFundModel expiryFundModel;

	@Autowired
	private WalletListModel walletListModel;

	public void bulkAdd(List<BulkAddFundModel> bulkAddModel) throws WalletException {

		walletListModel = template.findById(walletListModel.getWalletListId(), WalletListModel.class);

		for (BulkAddFundModel bulkAddFundModel : bulkAddModel) {

			if (!walletListModel.getWalletIds().contains(bulkAddFundModel.getId())) {
				throw new DocumentDoesNotExistException();
			} 
			
			else 
			{
				WalletEntityModel walletEntityModel = template.findById(bulkAddFundModel.getId(),
						WalletEntityModel.class);
				if (!walletEntityModel.getCurrency().equals(bulkAddFundModel.getCurrency())) {

					String error = String.format(ErrorMessage.TRANSACTION_CURRENCY_NOT_EQ_WALLET_CURRENCY,
							bulkAddFundModel.getCurrency(), walletEntityModel.getCurrency());
					throw new WalletException(error, ErrorCode.BadRequest.getCode());

				}else {
					if(bulkAddFundModel.getHasExpiry()==true && bulkAddFundModel.getDaysOfExpiry()>0) {
						/*
						 * need to add fund in expiry
						 */
						expiryFundModel.setExpiryBalance(bulkAddFundModel.getFund());
						LocalDateTime today =  LocalDateTime.now(); 
						LocalDateTime expiryDate =  today.plusDays(bulkAddFundModel.getDaysOfExpiry()); 
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
						retrievePermanentBalance = retrievePermanentBalance + bulkAddFundModel.getFund();
						walletEntityModel.setPermnanentBalance(retrievePermanentBalance);
					}
					transactionModel.setFund(bulkAddFundModel.getFund());
					transactionModel.setOperation(operation);
					transactionModel.setUpdatedTime(LocalDateTime.now());
					
					List<TransactionModel> temp=walletEntityModel.getListOfTransaction();
					temp.add(transactionModel);
					walletEntityModel.setListOfTransaction(temp);
					template.save(walletEntityModel);
				}
			}
		}

	}

}
