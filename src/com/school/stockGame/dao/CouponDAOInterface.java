package com.school.stockGame.dao;

import java.util.List;

import com.school.stockGame.vo.CouponPurchaseVO;
import com.school.stockGame.vo.CouponVO;

public interface CouponDAOInterface {
	// 등록된 쿠폰 모두 조회
	public List<CouponVO> getCouponList();

	// 쿠폰 구매
	public String setBuyCoupon(String studentId, int couponPrice, String couponName, int state, int couponNo);

	// 학생이 구매한 쿠폰 개수 조회
	public int getMyCouponCount(String studentId);
 
	// 학생 보유 포인트 조회
	public int getStudentPoint(String studentId);

	// 쿠폰 구매 내역 등록
	public int setPurchaseRecord(String studentId, int couponNo, String couponName, int couponPrice, int state);

	// 학생 포인트 차감
	public int setStudentAssets(String studentId, int price);

	// 내가 보유한 쿠폰 조회
	public List<CouponPurchaseVO> getMyCouponList(String studentId);
}
