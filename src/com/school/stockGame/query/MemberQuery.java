package com.school.stockGame.query;

public interface MemberQuery {

	String ADD_MEMBER_SQL="INSERT INTO students (student_id, password, name, grade, class, class_number, register_year, total_coupon, total_point) " +
"VALUES (?, ?, ?, ?, ?, ?, EXTRACT(YEAR FROM SYSDATE), 0, 30000)";
	
	String LOGIN_SQL="SELECT name, grade, class, class_number, total_point FROM students WHERE student_id = ? AND password = ?";
	
	String ID_Check_SQL = "SELECT student_id FROM students WHERE student_id = ? ";
}
