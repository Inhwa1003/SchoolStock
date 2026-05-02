package com.school.stockGame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		conn = null;
		boolean sussess = false;
		try {
			conn = DBCP.getConnection();
			conn.setAutoCommit(false);
			int totalPoint = getStudentPoint(conn, studentId);
			if (totalPoint >= couponPrice) {
				int insertRecord = insertPurchaseRecord(conn, studentId, couponNo, couponName, couponPrice, state);
				int updateAssets = updateStudentAssets(conn, studentId, couponPrice);
				if (insertRecord == 1 && updateAssets == 1) {
					conn.commit();
					sussess = true;
				}else {
					conn.rollback();
				}
			}
		} catch (Exception e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (Exception ex) {
				e.printStackTrace();
			}
		}finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return sussess;
	}
	
	private int getStudentPoint(Connection conn, String studentId) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(CouponQuery.MY_POINT_SQL);
		stmt.setString(1, studentId);
		ResultSet rs = stmt.executeQuery();
		int point = 0;
		if (rs.next()) point =rs.getInt(1);
		rs.close();
		stmt.close();
		return point;
	}
	
	private int insertPurchaseRecord(Connection conn, String id, int no, String name, int price, int state) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(CouponQuery.BUY_COUPON_SQL);
		stmt.setInt(1, price);
		stmt.setString(2, name);
		stmt.setInt(3, state);
		stmt.setString(4, id);
		stmt.setInt(5, no);
		int result = stmt.executeUpdate();
		stmt.close();
		return result;
	}
	
	private int updateStudentAssets(Connection conn, String studentId, int price) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(CouponQuery.UPDATE_STUDENT_PURCHASE_SQL);
		stmt.setInt(1, price);
		stmt.setString(2, studentId);
		stmt.setInt(3, price);
		int result = stmt.executeUpdate();
		stmt.close();
		return result;
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
