package com.zomato.walletsystem.exception;

public class ErrorMessage {
	public static final String TRANSACTION_CURRENCY_NOT_EQ_WALLET_CURRENCY = "Transaction can't be saved. Transaction currency %s differs from wallet currency %s.";
	public static final String NO_WALLET_FOUND = "No wallet with id %s exists in the system.";
	public static final String NOT_ENOUGH_FUNDS = "Wallet %s has not enough funds to perform debit transaction with amount %.2f";
	public static final String NO_MANDATORY_FIELD = "Field %s is mandatory. It should be provided and can't be empty.";

}
