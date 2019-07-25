package com.zomato.walletsystem.exception;

public class WalletException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int errorCode;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public WalletException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public WalletException() {
		super();

	}

	public WalletException(String message) {
		super(message);
	}

	public WalletException(Exception e) {
		super(e);
	}

}