package com.school.stockGame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class MyPointHistoryDAO {
	public MyPointHistoryDAO() {}
	
	public Map<String, Object> myPointHistory(String studentId){
		Map<String, Object> tmp = new HashMap<>();
		try{
			Connection conn=DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(MyPointHistoryQuery.MY_POINT_HISTORY_SQL);
			stmt.setString(1, studentId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				tmp.put("student_id", studentId);
			}
			rs.close();
			stmt.close();
			conn.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return tmp;
	}
	

}