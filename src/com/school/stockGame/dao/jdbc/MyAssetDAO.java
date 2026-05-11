package com.school.stockGame.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.school.stockGame.dao.MyAssetDAOInterface;
import com.school.stockGame.query.MyAssetQuery;

// 각각의 매서드 무슨 기능을 수행하는 건지, 작성해주면 좋겠다고 생각함.

public class MyAssetDAO implements MyAssetDAOInterface {
	public MyAssetDAO(){}
	
	public int getMyValue(int stockNo, String studentId) {
		int result = 0;
		try {
			Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(MyAssetQuery.MY_VALUE_SQL);
			stmt.setInt(1, stockNo);
			stmt.setString(2, studentId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int getPointValue(String studentId) {
		int result = 0;
		try {
			Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(MyAssetQuery.TOTAL_POINT_SQL);
			stmt.setString(1, studentId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("total_point");
			}
			rs.close();
	        stmt.close();
	        conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int getTotalProfit(int stockNo, String studentId, String state) {
		int result = 0;
		try {
			Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(MyAssetQuery.TOTAL_PROFIT_SQL);
			stmt.setInt(1, stockNo);
			stmt.setString(2, studentId);
			stmt.setString(3, state);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("totalProfit");
			}
			rs.close();
	        stmt.close();
	        conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int getTotalCoupon(String studentId) {
		int result = 0;
		try {
			Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(MyAssetQuery.TOTAL_COUPON_SQL);
			stmt.setString(1, studentId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("total_coupon");
			}
			rs.close();
	        stmt.close();
	        conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String getStockName(int stockNo) {
		String result = null;
		try {
			Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(MyAssetQuery.STOCK_NAME_SQL);
			stmt.setInt(1, stockNo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getString("name");
			}
			rs.close();
	        stmt.close();
	        conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int getStockAmount(String studentId, int stockNo, String state) {
		int result = 0;
		try {
			Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(MyAssetQuery.STOCK_AMOUNT_SQL);
			stmt.setString(1, studentId);
			stmt.setInt(2, stockNo);
			stmt.setString(3, state);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("amount");
			}
			rs.close();
	        stmt.close();
	        conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	// 동석 추가 (트랜잭션 관리용)오버로딩
	public int getStockAmount(Connection conn, String studentId, int stockNo, String state) throws SQLException {
		int result = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(MyAssetQuery.STOCK_AMOUNT_SQL);
			stmt.setString(1, studentId);
			stmt.setInt(2, stockNo);
			stmt.setString(3, state);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("amount");
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ignore) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return result;
	}
	
	public int getAveragePrice(String studentId, int stockNo, String state, String content) {
		int result = 0;
		try {
			Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(MyAssetQuery.AVERAGE_PRICE_SQL);
			stmt.setString(1, studentId);
			stmt.setInt(2, stockNo);
			stmt.setString(3, state);
			stmt.setString(4, content);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("average");
			}
			rs.close();
	        stmt.close();
	        conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int getPurchasePrice(String studentId, int stockNo, String state, String content) {
		int result = 0;
		
		try {
			Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(MyAssetQuery.PURCHASE_PRICE_SQL);
			stmt.setString(1, studentId);
			stmt.setInt(2, stockNo);
			stmt.setString(3, state);
			stmt.setString(4, content);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("purchasePrice");
			}
			rs.close();
	        stmt.close();
	        conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int getStockProfit(String studentId, int stockNo, String state) {
		int result = 0;
		try {
			Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(MyAssetQuery.STOCK_PROFIT_SQL);
			stmt.setInt(1, stockNo);
			stmt.setString(2, studentId);
			stmt.setInt(3, stockNo);
			stmt.setString(4, state);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("profit");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Integer> getMyStockNos(String studentId, String state) {
	    List<Integer> list = new ArrayList<Integer>();
	    try {
	        Connection conn = DBCP.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(MyAssetQuery.GET_MY_STOCK_SQL);
	        stmt.setString(1, studentId);
	        stmt.setString(2, state);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            list.add(rs.getInt("stock_no"));
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
