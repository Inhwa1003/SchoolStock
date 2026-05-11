package com.school.stockGame.dao.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class DBCPMybatis {
	
	private static DBCPMybatis dbcp;
	private static SqlSessionFactory factory;
	private DBCPMybatis(){}
	
	public static SqlSessionFactory getSqlSessionFactory(){
		if(dbcp == null) dbcp = new DBCPMybatis();
		
		if(factory == null){
			String resource = "config/mybatis-Config.xml";
			InputStream in = null;
			
			try {
				in = Resources.getResourceAsStream(resource);
			} catch (IOException e) {
				e.printStackTrace();
				throw new NullPointerException("환경설정 오류");
			}
			
			factory = new SqlSessionFactoryBuilder().build(in);
			
		}
		return factory;
		
	}
	

}