package com.synergisticit.domain;

public class Transaction {

	private Long fromAccountId;
	private Long toAccountId;
	private double transactionAmount;
	private TransactionType trxType;
	private String comments;
	
	public Transaction() {
		
	}

	public Transaction(Long fromAccountId, Long toAccountId, double transactionAmount, TransactionType trxType, String comments) {
		super();
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.transactionAmount = transactionAmount;
		this.trxType = trxType;
		this.comments = comments;
	}

	public Long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(Long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public Long getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(Long toAccountId) {
		this.toAccountId = toAccountId;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public TransactionType getTrxType() {
		return trxType;
	}

	public void setTrxType(TransactionType trxType) {
		this.trxType = trxType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Transaction [fromAccountId=" + fromAccountId + ", toAccountId=" + toAccountId + ", transactionAmount="
				+ transactionAmount + ", comments=" + comments + "]";
	}
	
	
}
