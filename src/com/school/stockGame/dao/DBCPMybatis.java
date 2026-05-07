package com.school.stockGame.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBCPMybatis {
	private static DBCPMybatis dbcp;
	private static SqlSessionFactory factory;
	private DBCPMybatis(){}
	
	public static SqlSessionFactory getSqlSessionFactory() {
		if(dbcp == null) dbcp = new DBCPMybatis();
		InputStream in = null;
		if(factory == null) {
			String resource = "config/mybatis-Config.xml";
			try {
				in = Resources.getResourceAsStream(resource);
			} catch (IOException e) {
				throw new NullPointerException("ȯ�� ����");
			}
			factory = new SqlSessionFactoryBuilder().build(in);
		}
		return factory;
	}
}
