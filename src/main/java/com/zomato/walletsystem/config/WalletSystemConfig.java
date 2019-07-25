package com.zomato.walletsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.zomato.walletsystem.model.AddFundModel;
import com.zomato.walletsystem.model.BulkAddFundModel;
import com.zomato.walletsystem.model.ExpiryFundModel;
import com.zomato.walletsystem.model.TransactionListOutputModel;
import com.zomato.walletsystem.model.TransactionModel;
import com.zomato.walletsystem.model.WalletDetailOutputModel;
import com.zomato.walletsystem.model.WalletEntityModel;
import com.zomato.walletsystem.model.WalletListModel;
import com.zomato.walletsystem.service.AddFundApplicationService;
import com.zomato.walletsystem.service.BulkAddFundApplicationService;
import com.zomato.walletsystem.service.BulkExpirationApplicationService;
import com.zomato.walletsystem.service.CreateWalletApplicationService;
import com.zomato.walletsystem.service.GetTransactionHistoryApplicationService;
import com.zomato.walletsystem.service.GetWalletDetailsApplicationService;
import com.zomato.walletsystem.service.RemoveFundApplicationService;
import com.zomato.walletsystem.service.WalletListApplicationService;

@Configuration
public class WalletSystemConfig {
	
	@Bean
	public CreateWalletApplicationService  createWalletApplicationService() {
		return new CreateWalletApplicationService();
	}
	
	@Bean
	public AddFundApplicationService addFundApplicationService() {
		return new AddFundApplicationService();
	}

	@Bean
	public GetWalletDetailsApplicationService getWalletDetailsApplicationService() {
		return new GetWalletDetailsApplicationService();
	}
	
	@Bean
	public RemoveFundApplicationService removeFundApplicationService() {
		return new RemoveFundApplicationService();
	}
	
	@Bean
	@Scope("prototype")
	public WalletEntityModel walletEntityModel() {
		return new WalletEntityModel();
	}

	@Bean
	public AddFundModel addFundModel() {
		return new AddFundModel();
	}
	
	@Bean
	public ExpiryFundModel expiryFundModel() {
		return new ExpiryFundModel();
	}
	
	@Bean
	public WalletDetailOutputModel walletDetailOutputModel() {
		return new WalletDetailOutputModel();
	}
	
	@Bean
	public WalletListApplicationService walletListApplicationService() {
		return new WalletListApplicationService();
	}
	
	@Bean
	public WalletListModel walletListModel() {
		return new WalletListModel();
	}
	
	@Bean
	public TransactionListOutputModel transactionListOutputModel() {
		return new TransactionListOutputModel();
	}
	
	@Bean
	public TransactionModel transactionModel() {
		return new TransactionModel();
	}
	
	@Bean
	public GetTransactionHistoryApplicationService getTransactionHistoryApplicationService() {
		return new GetTransactionHistoryApplicationService();
	}
	
	@Bean
	public BulkAddFundApplicationService bulkAddFundApplicationService() {
		return new BulkAddFundApplicationService();
	}
	
	@Bean
	public BulkAddFundModel bulkAddFundModel() {
		return new BulkAddFundModel();
	}
	
	@Bean
	public BulkExpirationApplicationService bulkExpirationApplicationService() {
		return new BulkExpirationApplicationService();
	}
}
