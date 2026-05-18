package com.school.stockGame.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.school.stockGame.dao.CouponDAOInterface;
import com.school.stockGame.query.CouponQuery;
import com.school.stockGame.vo.CouponPurchaseVO;
import com.school.stockGame.vo.CouponVO;

public class CouponDAOJdbc implements CouponDAOInterface{
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
	
	// 쿠폰 구매
	public String setBuyCoupon(String studentId, int couponPrice, String couponName, int state, int couponNo) {
		conn = null;
		String message = "쿠폰 구매에 실패했습니다.";

		try {
			conn = DBCP.getConnection();
			conn.setAutoCommit(false);

			// 1. 학생이 이미 구매한 쿠폰 개수 확인
			int couponCount = getMyCouponCount(conn, studentId);

			if (couponCount >= 3) {
				conn.rollback();
				message = "쿠폰은 최대 3개까지만 구매할 수 있습니다.";
				return message;
			}

			// 2. 학생 보유 포인트 확인
			int totalPoint = getStudentPoint(conn, studentId);

			if (totalPoint >= couponPrice) {
				// 3. 쿠폰 구매 내역 등록
				int insertRecord = setPurchaseRecord(conn, studentId, couponNo, couponName, couponPrice, state);

				// 4. 학생 포인트 차감 및 보유 쿠폰 수량 증가
				int updateAssets = setStudentAssets(conn, studentId, couponPrice);

				// 5. 둘 다 성공하면 commit
				if (insertRecord == 1 && updateAssets == 1) {
					conn.commit();
					message = "쿠폰 구매가 완료되었습니다.";
				} else {
					conn.rollback();
					message = "쿠폰 구매에 실패했습니다.";
				}
			} else {
				conn.rollback();
				message = "보유 포인트가 부족합니다.";
			}

		} catch (Exception e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			message = "쿠폰 구매 중 오류가 발생했습니다.";
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return message;
	}
	// 학생이 구매한 쿠폰 개수 조회
	public int getMyCouponCount(String studentId){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int count = 0;
		
		try { 
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(CouponQuery.MY_COUPON_COUNT_SQL);
			stmt.setString(1, studentId);
			rs = stmt.executeQuery();
         
			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
	        if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
	        if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
		}
		return count;
	}
	
	// 학생이 구매한 쿠폰 개수 조회(트랜잭션 관리용)
	public int getMyCouponCount(Connection conn, String studentId) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(CouponQuery.MY_COUPON_COUNT_SQL);
		stmt.setString(1, studentId);
		ResultSet rs = stmt.executeQuery();

		int count = 0;

		if (rs.next()) {
			count = rs.getInt(1);
		}

		rs.close();
		stmt.close();

		return count;
	}
	
	// 학생 보유 포인트 조회
	public int getStudentPoint(String studentId){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int point = 0;
		
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(CouponQuery.MY_POINT_SQL);
			stmt.setString(1, studentId);
			rs = stmt.executeQuery();	

			if (rs.next()) {
				point = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
	        if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
	        if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
		}
		return point;
	}

	// 학생 보유 포인트 조회 (트랜잭션 관리용)
	public int getStudentPoint(Connection conn, String studentId) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(CouponQuery.MY_POINT_SQL);
		stmt.setString(1, studentId);
		ResultSet rs = stmt.executeQuery();

		int point = 0;

		if (rs.next()) {
			point = rs.getInt(1);
		}

		rs.close();
		stmt.close();

		return point;
	}
	
	// 쿠폰 구매 내역 등록
	public int setPurchaseRecord(String studentId, int couponNo, String couponName, int couponPrice, int state){
		Connection conn = null;
		PreparedStatement stmt = null;
		int result = 0;
		
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(CouponQuery.BUY_COUPON_SQL);
			stmt.setInt(1, couponPrice);
			stmt.setString(2, couponName);
			stmt.setInt(3, state);
			stmt.setString(4, studentId);
			stmt.setInt(5, couponNo);

			result = stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
	        if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
		}
		return result;
	}
	
	// 쿠폰 구매 내역 등록 (트랜잭션 관리용)
	public int setPurchaseRecord(Connection conn, String studentId, int couponNo, String couponName, int couponPrice, int state) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(CouponQuery.BUY_COUPON_SQL);
		stmt.setInt(1, couponPrice);
		stmt.setString(2, couponName);
		stmt.setInt(3, state);
		stmt.setString(4, studentId);
		stmt.setInt(5, couponNo);

		int result = stmt.executeUpdate();

		stmt.close();

		return result;
	}
	
	// 학생 포인트 차감 및 보유 쿠폰 수량 증가
	public int setStudentAssets(String studentId, int price){
		Connection conn = null;
		PreparedStatement stmt = null;
		
		int result = 0;
		
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(CouponQuery.UPDATE_STUDENT_PURCHASE_SQL);
			stmt.setInt(1, price);
			stmt.setString(2, studentId);
			stmt.setInt(3, price);

			result = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
	        if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
		}
		return result;
	}

	// 학생 포인트 차감 및 보유 쿠폰 수량 증가(트랜잭션 관리용)
	public int setStudentAssets(Connection conn, String studentId, int price) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(CouponQuery.UPDATE_STUDENT_PURCHASE_SQL);
		stmt.setInt(1, price);
		stmt.setString(2, studentId);
		stmt.setInt(3, price);

		int result = stmt.executeUpdate();

		stmt.close();

		return result;
	}

	// 내가 보유한 쿠폰 조회
	public List<CouponPurchaseVO> getMyCouponList(String studentId){
		List<CouponPurchaseVO> list = new ArrayList<>();
		try {
			conn = DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(CouponQuery.MY_COUPON_SQL);
			stmt.setString(1, studentId);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				list.add(new CouponPurchaseVO(rs.getInt("price"), rs.getString("name")));
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