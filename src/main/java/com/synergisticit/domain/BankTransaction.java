package com.synergisticit.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "bankTransaction")
public class BankTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long trxId;
	
	private Long trxFromAccount;
	
	private Long trxToAccount;
	
	private double trxAmount;
	
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime trxLocalDateTime;
	
	private String comments;
	
	public BankTransaction() {
		
	}

	public Long getTrxId() {
		return trxId;
	}

	public void setTrxId(Long trxId) {
		this.trxId = trxId;
	}

	public Long getTrxFromAccount() {
		return trxFromAccount;
	}

	public void setTrxFromAccount(Long trxFromAccount) {
		this.trxFromAccount = trxFromAccount;
	}

	public Long getTrxToAccount() {
		return trxToAccount;
	}

	public void setTrxToAccount(Long trxToAccount) {
		this.trxToAccount = trxToAccount;
	}

	public double getTrxAmount() {
		return trxAmount;
	}

	public void setTrxAmount(double trxAmount) {
		this.trxAmount = trxAmount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public LocalDateTime getTrxLocalDateTime() {
		return trxLocalDateTime;
	}

	public void setTrxLocalDateTime(LocalDateTime trxLocalDateTime) {
		this.trxLocalDateTime = trxLocalDateTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "BankTransaction [trxId=" + trxId + ", trxFromAccount=" + trxFromAccount + ", trxToAccount="
				+ trxToAccount + ", trxAmount=" + trxAmount + ", transactionType=" + transactionType
				+ ", trxLocalDateTime=" + trxLocalDateTime + ", comments=" + comments + "]";
	}
	
}
