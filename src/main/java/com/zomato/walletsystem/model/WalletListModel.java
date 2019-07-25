package com.zomato.walletsystem.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

@Document
public class WalletListModel {
	public String getWalletListId() {
		return walletListId;
	}


	public void setWalletListId(String walletListId) {
		this.walletListId = walletListId;
	}


	@Id
	public String walletListId = "111"; 
	
		
	@Field 
	private Set<String> walletIds = new HashSet<>();


	public Set<String> getWalletIds() {
		return walletIds;
	}


	public void setWalletIds(Set<String> walletIds) {
		this.walletIds = walletIds;
	}



}
