package com.zomato.walletsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;

import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.zomato.walletsystem.exception.ErrorCode;
import com.zomato.walletsystem.exception.ErrorMessage;
import com.zomato.walletsystem.exception.WalletException;
import com.zomato.walletsystem.model.ExpiryFundModel;
import com.zomato.walletsystem.model.WalletDetailOutputModel;
import com.zomato.walletsystem.model.WalletEntityModel;
import com.zomato.walletsystem.model.WalletListModel;

public class GetWalletDetailsApplicationService {

	@Autowired
    private CouchbaseTemplate template;
	
	@Autowired
	private WalletListModel walletListModel;

	
	@Autowired
	private WalletDetailOutputModel walletDetailOutputModel;

	public WalletDetailOutputModel getWalletDetails(String uniqueName) throws WalletException {
		try {

			walletListModel = template.findById(walletListModel.getWalletListId(), WalletListModel.class);
			if(!walletListModel.getWalletIds().contains(uniqueName)) {
				throw new DocumentDoesNotExistException();

			}

					WalletEntityModel walletEntityModel  = template.findById(uniqueName, WalletEntityModel.class);
			double retrievePermanentBalance = walletEntityModel.getPermnanentBalance();
			List<ExpiryFundModel> temp = walletEntityModel.getListOfExpiryFund();
			double total_balance =0;
			double expirySum=0;
			for(ExpiryFundModel expiryFundModel : temp) {
				expirySum = expirySum+expiryFundModel.getExpiryBalance();
			}
			total_balance = retrievePermanentBalance + expirySum;
			
			walletDetailOutputModel.setBalance(total_balance);
			walletDetailOutputModel.setCurrency(walletEntityModel.getCurrency());
			walletDetailOutputModel.setUniqueName(walletEntityModel.getUniqueName());
			
			return walletDetailOutputModel;
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
