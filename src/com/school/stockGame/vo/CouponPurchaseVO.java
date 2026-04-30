package com.school.stockGame.vo;

public class CouponPurchaseVO extends CouponVO{
	private int couponPurchaseNo;
	private String purchaseDate;
	private int price;
	private String name;
	private String state;
	private int studentId;
	
	public CouponPurchaseVO(int couponNo, int couponPurchaseNo, String purchaseDate, int price, String name,
			String state, int studentId) {
		super(couponNo);
		setCouponPurchaseNo(couponPurchaseNo);
		setPurchaseDate(purchaseDate);
		setPrice(price);
		setName(name);
		setState(state);
		setStudentId(studentId);
	}
	public int getCouponPurchaseNo() {
		return couponPurchaseNo;
	}
	public void setCouponPurchaseNo(int couponPurchaseNo) {
		this.couponPurchaseNo = couponPurchaseNo;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
}
		