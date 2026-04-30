package com.school.stockGame.vo;

public class OrderVO extends StudentVO {
	private int orderNo;
	private String content;
	private int price;
	private int amount;
	private String state;
	private String orderDate;
	
	public OrderVO(){};
	// 동석 추가
	public OrderVO(int price, int amount, String content){
		setPrice(price);
		setAmount(amount);
		setContent(content);
	}
	// 동석 추가
	public OrderVO(int orderNo, String content, int price, int amount, String orderDate, String state){
		setOrderNo(orderNo);
		setContent(content);
		setPrice(price);
		setAmount(amount);
		setOrderDate(orderDate);
		setState(state);
	}
	public OrderVO(String studentId, int orderNo, String content, int price, int amount, String state, String orderDate) {
		super(studentId);
		setOrderNo(orderNo);
		setContent(content);
		setPrice(price);
		setAmount(amount);
		setState(state);
		setOrderDate(orderDate);
	}
	
	
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	// 동석 추가
	@Override
	public String toString() {
		return "[orderNo=" + orderNo + ", content=" + content + ", price=" + price + ", amount=" + amount
				+ ", state=" + state + ", orderDate=" + orderDate + "]";
	}

	
	
}