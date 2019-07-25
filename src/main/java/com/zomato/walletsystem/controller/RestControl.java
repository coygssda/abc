package com.zomato.walletsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zomato.walletsystem.exception.WalletException;
import com.zomato.walletsystem.model.AddFundModel;
import com.zomato.walletsystem.model.BulkAddFundModel;
import com.zomato.walletsystem.model.CreateWalletModel;
import com.zomato.walletsystem.model.RemoveFundModel;
import com.zomato.walletsystem.model.TransactionListOutputModel;
import com.zomato.walletsystem.model.WalletDetailOutputModel;
import com.zomato.walletsystem.service.AddFundApplicationService;
import com.zomato.walletsystem.service.BulkAddFundApplicationService;
import com.zomato.walletsystem.service.BulkExpirationApplicationService;
import com.zomato.walletsystem.service.CreateWalletApplicationService;
import com.zomato.walletsystem.service.GetTransactionHistoryApplicationService;
import com.zomato.walletsystem.service.GetWalletDetailsApplicationService;
import com.zomato.walletsystem.service.RemoveFundApplicationService;
import com.zomato.walletsystem.service.WalletListApplicationService;

@RestController
@RequestMapping("/zomato")
@ComponentScan
public class RestControl {
	
	@Autowired
	private CreateWalletApplicationService createWalletApplicationService;
	
	@Autowired
	private AddFundApplicationService addFundApplicationService;
	
	@Autowired
	private RemoveFundApplicationService removeFundApplicationService;
	
	@Autowired
	private GetWalletDetailsApplicationService getWalletDetailsApplicationService;
	
	@Autowired
	private WalletListApplicationService walletListApplicationService;
	
	@Autowired
	private GetTransactionHistoryApplicationService getTransactionHistoryApplicationService;
	
	@Autowired
	private BulkAddFundApplicationService bulkAddFundApplicationService;
	
	@Autowired
	private BulkExpirationApplicationService bulkExpirationApplicationService;


	@RequestMapping(value = "/createWallet", method = RequestMethod.POST)
	public ResponseEntity<String> createWallet(@RequestBody CreateWalletModel createWalletModel)
			throws Exception {
		
		createWalletApplicationService.createWallet(createWalletModel);

		return new ResponseEntity<String>("New Wallet Created Successfully",HttpStatus.OK);

	}

	@RequestMapping(value = "/addFund/{uniqueName}", method = RequestMethod.POST)
	public ResponseEntity<AddFundModel> addFund(@PathVariable("uniqueName") String uniqueName,@RequestBody AddFundModel addFundModel) throws Exception {
		
		addFundApplicationService.addFund(addFundModel,uniqueName);

		return new ResponseEntity<AddFundModel>(HttpStatus.OK);
	}

	@RequestMapping(value = "/removeFund/{uniqueName}", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeFund(@PathVariable("uniqueName") String uniqueName,@RequestBody RemoveFundModel removeFundModel) throws WalletException {

		removeFundApplicationService.removeFund(removeFundModel,uniqueName);
		return new ResponseEntity<String>(HttpStatus.OK);

	}

	@RequestMapping(value = "/wallet/{uniqueName}", method = RequestMethod.GET)
	public ResponseEntity<WalletDetailOutputModel> getWalletDetails(@PathVariable("uniqueName") String uniqueName) throws Exception {

		WalletDetailOutputModel walletDetailOutputModel = getWalletDetailsApplicationService.getWalletDetails(uniqueName);
		return new ResponseEntity<WalletDetailOutputModel>(walletDetailOutputModel,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/walletList", method = RequestMethod.POST)
	public ResponseEntity<String> createWalletList() throws Exception{
		walletListApplicationService.make();
		
		return new ResponseEntity<>(HttpStatus.OK);
 
	}
	
	@RequestMapping(value = "/transactionHistory/{uniqueName}", method = RequestMethod.GET)
	public ResponseEntity<TransactionListOutputModel> getTransactionHistory(@PathVariable("uniqueName") String uniqueName) throws Exception {
TransactionListOutputModel list=new TransactionListOutputModel();
		 list=getTransactionHistoryApplicationService.getTransactionHistory(uniqueName);
		return new ResponseEntity<TransactionListOutputModel>(list,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/bulkAddFund", method = RequestMethod.POST)
	public ResponseEntity<String> addInBulk(@RequestBody List<BulkAddFundModel> listOfAddFund) throws Exception {
		
		bulkAddFundApplicationService.bulkAdd(listOfAddFund);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/bulkExpiration", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeBulk() throws Exception {
		bulkExpirationApplicationService.bulkRemoval();
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
