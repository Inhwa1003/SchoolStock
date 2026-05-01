package com.school.stockGame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.school.stockGame.query.CouponQuery;
import com.school.stockGame.vo.CouponPurchaseVO;
import com.school.stockGame.vo.CouponVO;

public class CouponDAO {
	private Connection conn;
	
	// 등록된 쿠폰 모두 조회
	public List<CouponVO> getCouponList(){
		List<CouponVO> list = new ArrayList<>();
		try {
			conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(CouponQuery.COUPON_LIST_SQL);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				list.add(new CouponVO(rs.getInt(1), rs.getString(2), rs.getInt(3)));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean setBuyCoupon(String studentId, int couponPrice, String couponName, int state, int couponNo) {
		boolean flag = false;
		try {
			conn = DBCP.getConnection();
			
			conn.setAutoCommit(false);
			
			PreparedStatement stmt = conn.prepareStatement(CouponQuery.MY_POINT_SQL);
			stmt.setString(1, studentId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				// 보유한 포인트가 구매하려는 쿠폰 가격보다 여유가 있거나 같다면 실행
				if(rs.getInt(1) >= couponPrice){
					stmt = conn.prepareStatement(CouponQuery.BUY_COUPON_SQL);
					stmt.setInt(1, couponPrice);
					stmt.setString(2, couponName);
					stmt.setInt(3, state);
					stmt.setString(4, studentId);
					stmt.setInt(5, couponNo);
					// 쿠폰 구매내역 추가 되면 실행
					if(stmt.executeUpdate() == 1);
						stmt = conn.prepareStatement(CouponQuery.UPDATE_STUDENT_PURCHASE_SQL);
						stmt.setInt(1, couponPrice);
						stmt.setString(2, studentId);
						stmt.setInt(3, couponPrice);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	// 내가 보유한 쿠폰 조회
	public List<CouponPurchaseVO> MyCouponList(String studentId){
		List<CouponPurchaseVO> list = new ArrayList<>();
		try {
			conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(CouponQuery.MY_COUPON_SQL);
			stmt.setString(1, studentId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				list.add((CouponPurchaseVO) new CouponPurchaseVO(rs.getInt("price"),rs.getString("name")));
			}
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
