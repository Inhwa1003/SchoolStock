package com.school.stockGame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.school.stockGame.query.MyAssetQuery;

public class MyAssetDAO {
	public MyAssetDAO(){}
	
	public int getMyValue(int stockNo, String studentId) {
		int result = 0;
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(MyAssetQuery.MY_VALUE_SQL);
			stmt.setInt(1, stockNo);
			stmt.setString(2, studentId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}
		return result;
	}
	
	public int getPointValue(String studentId) {
		int result = 0;
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(MyAssetQuery.TOTAL_POINT_SQL);
			stmt.setString(1, studentId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("total_point");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}
		return result;
	}
	
	public int getTotalProfit(int stockNo, String studentId, String state) {
		int result = 0;
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(MyAssetQuery.TOTAL_PROFIT_SQL);
			stmt.setInt(1, stockNo);
			stmt.setString(2, studentId);
			stmt.setString(3, state);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("totalProfit");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}
		return result;
	}
	
	public int getTotalCoupon(String studentId) {
		int result = 0;
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(MyAssetQuery.TOTAL_COUPON_SQL);
			stmt.setString(1, studentId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("total_coupon");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}
		return result;
	}
	
	public String getStockName(String studentId) {
		String result = null;
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(MyAssetQuery.STOCK_NAME_SQL);
			stmt.setString(1, studentId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getString("name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}
		return result;
	}
	
	public int getStockAmount(String studentId, int stockNo, String state) {
		int result = 0;
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(MyAssetQuery.STOCK_AMOUNT_SQL);
			stmt.setString(1, studentId);
			stmt.setInt(2, stockNo);
			stmt.setString(3, "체결");
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}
		return result;
	}
	
	public int getAveragePrice(String studentId, int stockNo) {
		int result = 0;
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(MyAssetQuery.AVERAGE_PRICE_SQL);
			stmt.setString(1, studentId);
			stmt.setInt(2, stockNo);
			stmt.setString(3, "매수");
			stmt.setString(4, "체결");
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}
		return result;
	}
	
	public int getPurchasePrice(String studentId, int stockNo, String state, String content) {
		int result = 0;
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(MyAssetQuery.PURCHASE_PRICE_SQL);
			stmt.setString(1, studentId);
			stmt.setInt(2, stockNo);
			stmt.setString(3, "체결");
			stmt.setString(4, "매수");
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}
		return result;
	}
	
	public int getStockProfit(String studentId, int stockNo, String state) {
		int result = 0;
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
		try {
			conn = DBCP.getConnection();
			stmt = conn.prepareStatement(MyAssetQuery.STOCK_PROFIT_SQL);
			stmt.setInt(1, stockNo);
			stmt.setString(2, studentId);
			stmt.setInt(3, stockNo);
			stmt.setString(4, "체결");
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("profit");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
		try {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}