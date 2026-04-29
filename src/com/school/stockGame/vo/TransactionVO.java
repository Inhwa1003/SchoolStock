package com.school.stockGame.vo;

public class TransactionVO{
	private int transactionNo;
	private String transactionDate;
	private int sellOrderNo;
	private int buyOrderNo;
	
	public TransactionVO(int buyOrderNo, int sellOrderNo, int transactionNo, String transactionDate){
		setBuyOrderNo(buyOrderNo);
		setSellOrderNo(sellOrderNo);
		setTransactionNo(transactionNo);
		setTransactionDate(transactionDate);
	}

	public int getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(int transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public int getSellOrderNo() {
		return sellOrderNo;
	}

	public void setSellOrderNo(int sellOrderNo) {
		this.sellOrderNo = sellOrderNo;
	}

	public int getBuyOrderNo() {
		return buyOrderNo;
	}

	public void setBuyOrderNo(int buyOrderNo) {
		this.buyOrderNo = buyOrderNo;
	}
	


}
