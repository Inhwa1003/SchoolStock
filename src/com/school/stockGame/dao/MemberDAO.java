package com.school.stockGame.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.school.stockGame.query.MemberQuery;

//import com.school.stockGame.vo.studentVO
public class MemberDAO {
	public MemberDAO(){}
	public boolean addMember(String studentId, String password,
			String name,int grade, String className,
			int classNumber){
		boolean flag = false;
		try{
			Connection conn=DBCP.getConnection();
			PreparedStatement stmt = conn.prepareStatement(
					MemberQuery.ADD_MEMBER_SQL);
			stmt.setString(1, studentId);
			stmt.setString(2, password);
			stmt.setString(3, name);
			stmt.setInt(4, grade);
			stmt.setString(5, className);
			stmt.setInt(6, classNumber);
			flag = (stmt.executeUpdate() == 1);
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
		}
	public Map<String, Object> login(String studentId, String password) {		
		Map<String, Object> tmp=new HashMap<>();
		try {
			Connection conn=DBCP.getConnection();			
			PreparedStatement stmt = conn.prepareStatement(MemberQuery.LOGIN_SQL);
			stmt.setString(1, studentId);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				tmp.put("studentId", studentId);
				tmp.put("name", rs.getString(1));
				tmp.put("grade", rs.getInt(2));
				tmp.put("class", rs.getString(3));
				tmp.put("classNumber", rs.getInt(4));
				tmp.put("totalPoint", rs.getInt(5));
			}
			rs.close();		
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmp;
	}
	public boolean idCheck(String studentId) {
		boolean result=false;
		try {
			Connection conn=DBCP.getConnection();			
			PreparedStatement stmt = conn.prepareStatement(MemberQuery.ID_CHECK_SQL);
			stmt.setString(1, studentId);
			ResultSet rs = stmt.executeQuery();
			result=rs.next();
			rs.close();		
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	}
  

