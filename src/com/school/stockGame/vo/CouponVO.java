package com.school.stockGame.vo;

public class CouponVO {
	private int couponNo;
	private String name;
	private int price;

	
	
	public CouponVO(int couponNo) {
		setCouponNo(couponNo);
	}
	public CouponVO(int couponNo, String name, int price) {
		setCouponNo(couponNo);
		setName(name);
		setPrice(price);
	}
	public int getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(int couponNo) {
		this.couponNo = couponNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}





}