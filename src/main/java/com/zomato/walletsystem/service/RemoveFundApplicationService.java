package com.zomato.walletsystem.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;

import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.zomato.walletsystem.exception.ErrorCode;
import com.zomato.walletsystem.exception.ErrorMessage;
import com.zomato.walletsystem.exception.WalletException;
import com.zomato.walletsystem.model.ExpiryFundModel;
import com.zomato.walletsystem.model.RemoveFundModel;
import com.zomato.walletsystem.model.TransactionModel;
import com.zomato.walletsystem.model.WalletEntityModel;
import com.zomato.walletsystem.model.WalletListModel;

public class RemoveFundApplicationService {

	@Autowired
	private CouchbaseTemplate template;

	@Autowired
	private WalletListModel walletListModel;

	private String operation = "Removed";

	@Autowired
	private TransactionModel transactionModel;

	public void removeFund(RemoveFundModel removeFundModel, String uniqueName) throws WalletException {
		try {

			walletListModel = template.findById(walletListModel.getWalletListId(), WalletListModel.class);
			if (!walletListModel.getWalletIds().contains(uniqueName)) {
				throw new DocumentDoesNotExistException();

			} else {
				WalletEntityModel walletEntityModel = template.findById(uniqueName, WalletEntityModel.class);
				if (!walletEntityModel.getCurrency().equals(removeFundModel.getCurrency())) {
					/*
					 * throw exception when currency does not match
					 */
					String error = String.format(ErrorMessage.TRANSACTION_CURRENCY_NOT_EQ_WALLET_CURRENCY,
							removeFundModel.getCurrency(), walletEntityModel.getCurrency());
					throw new WalletException(error, ErrorCode.BadRequest.getCode());

				} else {
					double retrievePermanentBalance = walletEntityModel.getPermnanentBalance();
					List<ExpiryFundModel> temp = walletEntityModel.getListOfExpiryFund();
					Collections.sort(temp, new CustomComparator());
					double total_balance = 0;
					double expirySum = 0;
					for (ExpiryFundModel expiryFundModel : temp) {
						expirySum = expirySum + expiryFundModel.getExpiryBalance();
					}
					total_balance = retrievePermanentBalance + expirySum;

					if (total_balance < removeFundModel.getRemovableFund()) {
						/*
						 * as balance is less we can't purchase anything
						 */
						String error = String.format(ErrorMessage.NOT_ENOUGH_FUNDS, walletEntityModel.getUniqueName(),
								removeFundModel.getRemovableFund());
						throw new WalletException(error, ErrorCode.BadRequest.getCode());

					} else {
						double sumToBeRemoved = removeFundModel.getRemovableFund();
						if (sumToBeRemoved > retrievePermanentBalance) {

							sumToBeRemoved = sumToBeRemoved - retrievePermanentBalance;
							walletEntityModel.setPermnanentBalance(0);
							int i = 0;
							double sumToBeAdded = 0;
							for (ExpiryFundModel expiryFundModel : temp) {
								if (expiryFundModel.getExpiryBalance() > sumToBeRemoved) {
									sumToBeAdded = expiryFundModel.getExpiryBalance() - sumToBeRemoved;
									break;
								}
								sumToBeRemoved = sumToBeRemoved - expiryFundModel.getExpiryBalance();
								i++;
							}

							for (int k = 0; k < i; k++) {
								temp.remove(k);
							}
							if (temp.size() > 0) {
								temp.get(0).setExpiryBalance(sumToBeAdded);
							}
							walletEntityModel.setListOfExpiryFund(temp);

						} else {
							retrievePermanentBalance = retrievePermanentBalance - sumToBeRemoved;
							walletEntityModel.setPermnanentBalance(retrievePermanentBalance);
						}
						transactionModel.setFund(removeFundModel.getRemovableFund());
						transactionModel.setOperation(operation);
						transactionModel.setUpdatedTime(LocalDateTime.now());

						List<TransactionModel> x = walletEntityModel.getListOfTransaction();
						x.add(transactionModel);
						walletEntityModel.setListOfTransaction(x);

						template.save(walletEntityModel);
					}
				}
			}
		} catch (Exception e) {
			if (e instanceof DocumentDoesNotExistException) {
				String error = String.format(ErrorMessage.NO_WALLET_FOUND, uniqueName);
				throw new WalletException(error, ErrorCode.BadRequest.getCode());
			} else {
				throw e;
			}
		}
	}

}

class CustomComparator implements Comparator<ExpiryFundModel> {

	@Override
	public int compare(ExpiryFundModel o1, ExpiryFundModel o2) {

		return o1.getExpiryDate().compareTo(o2.getExpiryDate());
	}

}
