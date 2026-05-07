package com.school.stockGame.vo;

public class CouponPurchaseVO extends CouponVO{
	private int couponPurchaseNo;
	private String purchaseDate;
	private int price;
	private String name;
	private String state;
	private String studentId;

	//내 보유쿠폰에서 가져올  생성자
	public CouponPurchaseVO(int price, String name) {
		setPrice(price);
		setName(name);
	}
	
	public CouponPurchaseVO(String studentId, int price) {
		setStudentId(studentId);
		setPrice(price);
	}
	
	public CouponPurchaseVO(String studentId, int price, String name, String state, int couponNo) {
		super(couponNo);
		setPrice(price);
		setName(name);
		setState(state);
		setStudentId(studentId);
	}

	public CouponPurchaseVO(int couponNo, int couponPurchaseNo, String purchaseDate, int price, String name,
			String state, String studentId) {
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
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}




}