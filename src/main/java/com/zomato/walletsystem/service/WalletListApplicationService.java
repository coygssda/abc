package com.zomato.walletsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;

import com.zomato.walletsystem.model.WalletListModel;

public class WalletListApplicationService {
	
	@Autowired
    private CouchbaseTemplate template;
	
	@Autowired
	private WalletListModel walletListModel;

	public void make() {
		
		template.save(walletListModel);
		
	}

}
