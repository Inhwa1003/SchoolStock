package com.school.stockGame.vo;

public class StockVO {
	private int stockNo;
	private String name;
	private String content;
	private int publicationBalance;
	private int publicationPrice;
	private int prevPrice;
	
	public StockVO() {}

	// 동석 추가 (인화님 주식리스트에서 눌렀을때 주식no가 필요해서 만듦)
	public StockVO(int stockNo, String name){
		setStockNo(stockNo);
		setName(name);
	}
	
	
	public int getStockNo() {
		return stockNo;
	}
	public void setStockNo(int stockNo) {
		this.stockNo = stockNo;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getPublicationBalance() {
		return publicationBalance;
	}
	public void setPublicationBalance(int publicationBalance) {
		this.publicationBalance = publicationBalance;
	}
	
	public int getPublicationPrice() {
		return publicationPrice;
	}
	public void setPublicationPrice(int publicationPrice) {
		this.publicationPrice = publicationPrice;
	}
	
	public int getPrevPrice() {
		return prevPrice;
	}
	public void setPrevPrice(int prevPrice) {
		this.prevPrice = prevPrice;
	}

}
