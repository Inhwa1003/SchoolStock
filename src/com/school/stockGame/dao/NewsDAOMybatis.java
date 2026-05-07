package com.school.stockGame.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class NewsDAOMybatis implements NewsDAOInterface {
	
	@Override
	public List<String> getNewsList() {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
		List<String> result = session.selectList("stockGameMapper.getContent");
		session.close();
		return result;
	}

}
