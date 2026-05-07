package com.school.stockGame.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.school.stockGame.vo.CouponPurchaseVO;
import com.school.stockGame.vo.CouponVO;

public interface CouponDAOInterface {
	List<CouponVO> getCouponList();
	boolean setBuyCoupon(String studentId, int couponPrice, String couponName, String state, int couponNo);
	int getStudentPoint(String studentId);
	int insertPurchaseRecord(String studentId, int couponNo, String couponName, int couponPrice, String state);
	int updateStudentAssets(String studentId, int price);
	List<CouponPurchaseVO> MyCouponList(String studentId);
}
