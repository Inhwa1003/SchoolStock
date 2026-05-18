package com.school.stockGame.dao.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.school.stockGame.dao.NewsDAOInterface;

public class NewsDAOMybatis implements NewsDAOInterface {

	@Override
	public List<String> getNewsList() {
		SqlSession session = DBCPMybatis.getSqlSessionFactory().openSession();
    
	    List<String> result = session.selectList("newsMapper.getNewsList");
		
	    session.close();
		return result;
	}

}