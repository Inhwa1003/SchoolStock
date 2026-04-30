package com.school.stockGame.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCP {
	private static DBCP dbcp;
	private DBCP() throws ClassNotFoundException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("driver loading");
	}
	//생성하자마자 던저주기때문에 팩토리 패턴이라고 함
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		if(dbcp==null) dbcp= new DBCP();// 한번만
		String uri = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		return DriverManager.getConnection(uri, "hr", "hr");
	}
}